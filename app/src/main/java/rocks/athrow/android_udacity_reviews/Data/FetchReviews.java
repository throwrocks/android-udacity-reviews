package rocks.athrow.android_udacity_reviews.Data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import rocks.athrow.android_udacity_reviews.ReviewListAdapter;
import rocks.athrow.android_udacity_reviews.ReviewsListFragmentActivity;

/**
 * Created by joselopez on 7/5/16.
 */
public class FetchReviews extends AsyncTask<String, Void, Void> {
    private static final String LOG_TAG = API.class.getSimpleName();
    private final Context mContext;
    private final ReviewListAdapter mAdapter;
    // Constructor
    public FetchReviews(Context context, ReviewListAdapter adapter) {
        this.mContext = context;
        this.mAdapter = adapter;

    }

    @Override
    protected Void doInBackground(String... params) {

        String jsonResults;
        ContentValues[] parsedResults;
        // Create an API object
        API mAPI = new API(mContext);
        // Get the results from the API
        jsonResults = mAPI.callAPI();
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


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mAdapter.notifyDataSetChanged();
    }
}
