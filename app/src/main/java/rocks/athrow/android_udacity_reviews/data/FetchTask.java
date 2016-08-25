package rocks.athrow.android_udacity_reviews.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.R;
import rocks.athrow.android_udacity_reviews.adapter.ReviewListAdapter;
import rocks.athrow.android_udacity_reviews.activity.MainActivity;

/**
 * FetchReviews
 * A class to fetch Review data from the Udacity API
 * and to handle calling the parse methods and the update database methods
 */
public class FetchTask extends AsyncTask<String, Void, Integer> {
    private final Context mContext;
    private final String module;
    private final ReviewListAdapter mAdapter;
    private MainActivity.ReviewsListFragmentCallback listener;
    private final String MODULE_REVIEWS = "submissions_completed";
    private final String MODULE_FEEDBACKS = "student_feedbacks";
    private final String APIKey;


    // Constructor
    public FetchTask(Context context, String module, ReviewListAdapter adapter, MainActivity.ReviewsListFragmentCallback listener) {
        SharedPreferences sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        this.APIKey = sharedPref.getString("api_key","");
        this.mContext = context;
        this.module = module;
        this.mAdapter = adapter;
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String jsonResults;
        ContentValues[] parsedResults = null;

        //------------------------------------------------------------------------------------------
        // Get the DateStart and DateEnd for the query parameters (get most recent only)
        //------------------------------------------------------------------------------------------
        Date dateStart = null;
        // Begin Realm Transaction
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        // REVIEWS - DateStart = Last completed_at date from the reviews
        if (module.equals(MODULE_REVIEWS)) {
            RealmQuery<RealmReview> reviewsQuery = realm.where(RealmReview.class);
            RealmResults<RealmReview> reviewsResult = reviewsQuery.findAll();
            if (reviewsResult.size() > 0) {
                dateStart = reviewsResult.maxDate("completed_at");
            }
        }
        // FEEDBACKS - DateStart = Last completed_at date from the feedbacks
        if (module.equals(MODULE_FEEDBACKS)) {
            RealmQuery<RealmFeedback> feedbacksQuery = realm.where(RealmFeedback.class);
            RealmResults<RealmFeedback> feedbacksResult = feedbacksQuery.findAll();
            if (feedbacksResult.size() > 0) {
                dateStart = feedbacksResult.maxDate("created_at");
            }else{
                // If this is the first time downloading reviews the API will only return the
                // last 30 days. Set the date start to when Udacity was founded to get all the
                // history
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(2011, 4, 1, 1, 0, 0);
                dateStart = cal.getTime();
            }
        }
        // Close realm
        realm.close();
        //------------------------------------------------------------------------------------------
        // Get the results from the API
        //------------------------------------------------------------------------------------------
        jsonResults = API.callAPI(APIKey, module, dateStart, null);
        //Parse the results if not null
        if (jsonResults != null) {
            if (module.equals(MODULE_REVIEWS)) {
                parsedResults = JSONParser.parseReviews(jsonResults);
            } else if (module.equals(MODULE_FEEDBACKS)) {
                parsedResults = JSONParser.parseFeedbacks(jsonResults);
            }
            // The parsedResults are not null, update the Realm database
            if (parsedResults != null) {
                UpdateRealm updateRealm = new UpdateRealm(mContext);
                if (module.equals(MODULE_REVIEWS)) {
                    //----------------------------------------------------------------------------------
                    // Update RealmReviews
                    //----------------------------------------------------------------------------------
                    updateRealm.updateReviews(parsedResults);
                } else if (module.equals(MODULE_FEEDBACKS)) {
                    //----------------------------------------------------------------------------------
                    // Update RealmFeedbacks
                    //----------------------------------------------------------------------------------
                    updateRealm.updateFeedbacks(parsedResults);
                }
                return 0;
            }
        }
        return -1;
    }


    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (!isCancelled()) {
            if (result == 0 && module.equals(MODULE_REVIEWS)) {
                mAdapter.notifyDataSetChanged();
            }
            if (listener != null) {
                listener.onFetchReviewsCompleted(result);
            }
        }
    }
}
