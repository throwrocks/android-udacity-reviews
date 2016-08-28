package rocks.athrow.android_udacity_reviews.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.activity.MainActivity;
import rocks.athrow.android_udacity_reviews.adapter.ReviewListAdapter;
import rocks.athrow.android_udacity_reviews.util.Utilities;

/**
 * FetchReviewsTask
 * Created by jose on 8/27/16.
 */
public class FetchReviewsTask extends AsyncTask<String, Void, Integer> {
    private final Context mContext;
    private final ReviewListAdapter mAdapter;
    private MainActivity.ReviewsListFragmentCallback listener;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private final String MODULE_REVIEWS = "submissions_completed";
    private final String APIKey;


    // Constructor
    public FetchReviewsTask(Context context, ReviewListAdapter adapter, MainActivity.ReviewsListFragmentCallback listener) {
        SharedPreferences sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        this.APIKey = sharedPref.getString("api_key", "");
        this.mContext = context;
        this.mAdapter = adapter;
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String jsonResults;
        ContentValues[] parsedResults;
        ContentValues apiParams = new ContentValues();
        apiParams.put("module", MODULE_REVIEWS);
        //------------------------------------------------------------------------------------------
        // Get the DateStart and DateEnd for the query parameters (get most recent only)
        //------------------------------------------------------------------------------------------
        Date dateStart;
        String dateStartString;
        // Begin Realm Transaction
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<RealmReview> reviewsQuery = realm.where(RealmReview.class);
        RealmResults<RealmReview> reviewsResult = reviewsQuery.findAll();
        if (reviewsResult.size() > 0) {
            dateStart = reviewsResult.maxDate("completed_at");
            dateStartString = Utilities.getDateAsString(dateStart, DATE_FORMAT, null);
        }else{
            dateStartString = "";
        }
        // Close realm
        realm.close();
        apiParams.put("date_start", dateStartString);
        apiParams.put("date_end", "");
        jsonResults = API.callAPI(APIKey, apiParams);
        //Parse the results if not null
        if (jsonResults != null)

        {
            parsedResults = JSONParser.parseReviews(jsonResults);
            // The parsedResults are not null, update the Realm database
            if (parsedResults != null) {
                UpdateRealm updateRealm = new UpdateRealm(mContext);
                updateRealm.updateReviews(parsedResults);
                return 0;
            }
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
            if (listener != null) {
                listener.onFetchReviewsCompleted(result);
            }
        }
    }
}