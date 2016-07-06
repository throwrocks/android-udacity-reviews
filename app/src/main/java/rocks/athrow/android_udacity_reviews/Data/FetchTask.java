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
        ContentValues[] parsedResults;
        // Create an API object
        API mAPI = new API(mContext);
        // TODO: get todays date/time and the last review's date/time and pass them as parameters so we only fetch what's needed
        // Get the results from the API
        jsonResults = mAPI.callAPI( module , "2016-06-16T10:25:58.841Z", "2016-07-17T10:30:26.393Z");
        //Parse the results if not null
        if (jsonResults != null) {
            Log.i("Parsed Results: ", "" + jsonResults);
            JSONParser parser = new JSONParser(mContext);
            parsedResults = parser.parseReviews(jsonResults);
            if (parsedResults != null) {
                UpdateRealm updateRealm = new UpdateRealm(mContext);
                updateRealm.updateReviews(parsedResults);
            }
        }
        Log.i("results", jsonResults);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mAdapter.notifyDataSetChanged();
        listener.onFetchReviewsCompleted();

    }
}
