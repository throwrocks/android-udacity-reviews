package rocks.athrow.android_udacity_reviews.data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.activity.MainActivity;
import rocks.athrow.android_udacity_reviews.adapter.ReviewListAdapter;
import rocks.athrow.android_udacity_reviews.util.Constants;
import rocks.athrow.android_udacity_reviews.util.Utilities;

/**
 * FetchFeedbacksTask
 * Created by jose on 8/27/16.
 */
public class FetchFeedbacksTask extends AsyncTask<String, Void, Integer> {
    private final Context mContext;
    private final ReviewListAdapter mAdapter;
    private final MainActivity.ReviewsListFragmentCallback mListener;
    private final String mAPIKey;

    public FetchFeedbacksTask(Context context, ReviewListAdapter adapter,
                              MainActivity.ReviewsListFragmentCallback listener) {
        this.mContext = context;
        PreferencesHelper mSharedPref = new PreferencesHelper(mContext);
        this.mAPIKey = mSharedPref.loadString(Constants.PREF_API_KEY, Constants.PREF_EMPTY_STRING);
        this.mAdapter = adapter;
        this.mListener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String jsonResults;
        ContentValues[] parsedResults;
        Date dateStart;
        ContentValues apiParams = new ContentValues();
        apiParams.put(Constants.API_MODULE, Constants.API_MODULE_FEEDBACKS);
        // Begin Realm Transaction
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<RealmFeedback> feedbacksQuery = realm.where(RealmFeedback.class);
        RealmResults<RealmFeedback> feedbacksResult = feedbacksQuery.findAll();
        if (feedbacksResult.size() > 0) {
            dateStart = feedbacksResult.maxDate("created_at");
        } else {
            // If this is the first time downloading reviews the API will only return the
            // last 30 days. Set the date start to when Udacity was founded to get all the
            // history
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(2011, 4, 1, 1, 0, 0);
            dateStart = cal.getTime();
        }
        // Close realm
        realm.close();
        apiParams.put("date_start", Utilities.getDateAsString(dateStart, Constants.UTIL_DATE_FORMAT, null));
        apiParams.put("date_end", "");
        jsonResults = API.callAPI(mAPIKey, apiParams);
        //Parse the results if not null
        parsedResults = JSONParser.parseFeedbacks(jsonResults);
        // The parsedResults are not null, update the Realm database
        if (parsedResults != null) {
            UpdateRealm updateRealm = new UpdateRealm(mContext);
            updateRealm.updateFeedbacks(parsedResults);
            return 0;
        }
        return -1;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (!isCancelled()) {
            if (result == 0) {
                mAdapter.notifyDataSetChanged();
            }
            if (mListener != null) {
                mListener.onFetchReviewsCompleted(result);
            }
        }
    }
}
