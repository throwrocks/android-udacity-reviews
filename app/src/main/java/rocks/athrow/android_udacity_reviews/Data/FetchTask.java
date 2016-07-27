package rocks.athrow.android_udacity_reviews.data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.activity.ReviewListAdapter;
import rocks.athrow.android_udacity_reviews.activity.MainActivity;

/**
 * FetchReviews
 * A class to fetch Review data from the Udacity API
 * and to handle calling the parse methods and the update database methods
 */
public class FetchTask extends AsyncTask<String, Void, Void> {
    private final Context mContext;
    private final String module;
    private final ReviewListAdapter mAdapter;
    private MainActivity.ReviewsListFragmentCallback listener;
    private final String MODULE_REVIEWS = "submissions_completed";
    private final String MODULE_FEEDBACKS = "student_feedbacks";


    // Constructor
    public FetchTask(Context context, String module, ReviewListAdapter adapter, MainActivity.ReviewsListFragmentCallback listener) {
        this.mContext = context;
        this.module = module;
        this.mAdapter = adapter;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        String jsonResults;
        ContentValues[] parsedResults = null;
        // Create an API object
        API mAPI = new API(mContext);
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
            }
        }
        // Close realm
        realm.close();
        //------------------------------------------------------------------------------------------
        // Get the results from the API
        //------------------------------------------------------------------------------------------
        jsonResults = mAPI.callAPI(module, dateStart, null);
        //Parse the results if not null
        if (jsonResults != null) {
            JSONParser parser = new JSONParser(mContext);
            if (module.equals(MODULE_REVIEWS)) {
                parsedResults = parser.parseReviews(jsonResults);
            } else if (module.equals(MODULE_FEEDBACKS)) {
                parsedResults = parser.parseFeedbacks(jsonResults);
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
            }
        }
        Log.i("results", jsonResults);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i("results", "" + isCancelled());
        if (!isCancelled()) {
            if (module.equals(MODULE_REVIEWS)) {
                mAdapter.notifyDataSetChanged();
            }
            if (listener != null) {
                listener.onFetchReviewsCompleted();
            }
        }

    }
}
