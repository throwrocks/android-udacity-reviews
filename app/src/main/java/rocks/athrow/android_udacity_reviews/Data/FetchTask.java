package rocks.athrow.android_udacity_reviews.Data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.ReviewListAdapter;
import rocks.athrow.android_udacity_reviews.ReviewsListActivity;
import rocks.athrow.android_udacity_reviews.Utilities;

/**
 * FetchReviews
 * A class to fetch Review data from the Udacity API
 * and to handle calling the parse methods and the update database methods
 */
public class FetchTask extends AsyncTask<String, Void, Void> {
    private final Context mContext;
    private final String module;
    private final ReviewListAdapter mAdapter;
    private ReviewsListActivity.ReviewsListFragmentCallback listener;
    private final String MODULE_REVIEWS = "submissions_completed";
    private final String MODULE_FEEDBACKS = "student_feedbacks";
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    // Constructor
    public FetchTask(Context context, String module, ReviewListAdapter adapter, ReviewsListActivity.ReviewsListFragmentCallback listener) {
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
        // Set the DateStart and DateEnd for the query parameters (get most recent only)
        //------------------------------------------------------------------------------------------
        // DateStart = Get the last completed date from the reviews
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<RealmReview> query = realm.where(RealmReview.class);
        RealmResults<RealmReview> results = query.findAll();
        Date dateStart = results.maxDate("completed_at");
        realm.close();
        // DateEnd = today's date
        Utilities util = new Utilities();
        Date dateEnd = util.getTodaysDate(DATE_FORMAT);
        //------------------------------------------------------------------------------------------
        // Get the results from the API
        //------------------------------------------------------------------------------------------
        jsonResults = mAPI.callAPI(module, dateStart, dateEnd);
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
