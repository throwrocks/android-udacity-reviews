package rocks.athrow.android_udacity_reviews.Data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import rocks.athrow.android_udacity_reviews.ReviewListAdapter;
import rocks.athrow.android_udacity_reviews.ReviewsListActivity;

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

    // Constructor
    public FetchTask(Context context, String module, ReviewListAdapter adapter, ReviewsListActivity.ReviewsListFragmentCallback listener) {
        this.mContext = context;
        this.module = module;
        this.mAdapter = adapter;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        String jsonResults = null;
        ContentValues[] parsedResults = null;
        // Create an API object
        API mAPI = new API(mContext);
        // TODO: get todays date/time and the last review's date/time and pass them as parameters so we only fetch what's needed
        // Get the results from the API
        jsonResults = mAPI.callAPI(module, null, null);
        //Parse the results if not null
        if (jsonResults != null) {
            Log.i("Parsed Results: ", "" + jsonResults);
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
        if (module.equals(MODULE_REVIEWS)) {
            mAdapter.notifyDataSetChanged();
            listener.onFetchReviewsCompleted();
        }

    }
}
