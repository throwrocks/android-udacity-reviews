package rocks.athrow.android_udacity_reviews.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.data.API;
import rocks.athrow.android_udacity_reviews.data.APIResponse;
import rocks.athrow.android_udacity_reviews.data.PreferencesHelper;
import rocks.athrow.android_udacity_reviews.data.RealmFeedback;
import rocks.athrow.android_udacity_reviews.data.RealmReview;
import rocks.athrow.android_udacity_reviews.data.UpdateRealm;
import rocks.athrow.android_udacity_reviews.util.Constants;
import rocks.athrow.android_udacity_reviews.util.Utilities;


public class SyncDataService extends IntentService {
    public static final String ACTION_SYNC_DATA = "rocks.athrow.android_udacity_reviews.service.action.FOO";

    public SyncDataService() {
        super("SyncDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SYNC_DATA.equals(action)) {
                downloadData();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void downloadData() {
        Intent response = new Intent(new Intent(ACTION_SYNC_DATA));
        Context context = getApplicationContext();
        PreferencesHelper prefs = new PreferencesHelper(getApplicationContext());
        String apiKey = prefs.loadString("api_key", "");
        if (apiKey.isEmpty()){
            response.putExtra("response_code", -1);
            LocalBroadcastManager.getInstance(this).sendBroadcast(response);
            return;
        }
        String jsonResults;
        // Get the DateStart and DateEnd for the query parameters (get most recent only)
        Date reviewsDateStart;
        Date feedbacksDateStart;
        String reviewsDateStartString;
        String feedbacksDateStartString;
        // Begin Realm Transaction
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<RealmReview> reviewsQuery = realm.where(RealmReview.class);
        RealmResults<RealmReview> reviewsResult = reviewsQuery.findAll();
        if (reviewsResult.size() > 0) {
            reviewsDateStart = reviewsResult.maxDate("completed_at");
            reviewsDateStartString = Utilities.getDateAsString(reviewsDateStart, Constants.UTIL_DATE_FORMAT, null);
        } else {
            reviewsDateStartString = "";
        }

        RealmQuery<RealmFeedback> feedbacksQuery = realm.where(RealmFeedback.class);
        RealmResults<RealmFeedback> feedbacksResult = feedbacksQuery.findAll();
        if (feedbacksResult.size() > 0) {
            feedbacksDateStart = feedbacksResult.maxDate("created_at");
        } else {
            // If this is the first time downloading reviews the API will only return the
            // last 30 days. Set the date start to when Udacity was founded to get all the
            // history
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(2011, 4, 1, 1, 0, 0);
            feedbacksDateStart = cal.getTime();
        }
        feedbacksDateStartString = Utilities.getDateAsString(feedbacksDateStart, Constants.UTIL_DATE_FORMAT, null);
        realm.close();

        APIResponse reviewsAPIResponse = API.callReviewsAPI(apiKey, reviewsDateStartString, null);
        int reviewsAPIResponseCode = reviewsAPIResponse.getResponseCode();
        if (reviewsAPIResponseCode == 200) {
            try {
                jsonResults = reviewsAPIResponse.getResponseText();
                JSONArray reviewsArray = new JSONArray(jsonResults);
                UpdateRealm updateRealm = new UpdateRealm(context);
                updateRealm.updateReviews(reviewsArray);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        APIResponse feedbacksAPIResponse = API.callFeedbacksAPI(apiKey, feedbacksDateStartString, null);
        int feedbacksAPIResponseCode = feedbacksAPIResponse.getResponseCode();
        if (feedbacksAPIResponseCode == 200) {
            try {
                jsonResults = feedbacksAPIResponse.getResponseText();
                JSONArray feedbacksArray = new JSONArray(jsonResults);
                UpdateRealm updateRealm = new UpdateRealm(context);
                updateRealm.updateFeedbacks(feedbacksArray);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        if ( reviewsAPIResponseCode == 200 && feedbacksAPIResponseCode == 200 ){
            response.putExtra("response_code", 200);
        }else{
            response.putExtra("response_code", 401);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(response);

    }

}
