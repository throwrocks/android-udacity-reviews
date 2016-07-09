package rocks.athrow.android_udacity_reviews.Data;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import rocks.athrow.android_udacity_reviews.BuildConfig;
import rocks.athrow.android_udacity_reviews.Utilities;

/**
 * API
 * This class manages the connection to the Udacity API
 * Created by joselopez on 3/10/16.
 */
public class API {

    private Context mContext;
    private static final String apiKey = BuildConfig.UDACITY_REVIEWER_API_KEY;
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    // Constructor
    public API(Context context) {
        this.mContext = context;
    }

    /**
     * callAPI
     * @param module the API module (supported: submittions_completed, student_feedbacks)
     * @param dateStart the start date to retrieve results from
     * @param dateEnd the end date to retrieve result to
     * @return the API response in a string
     */
    public String callAPI(String module, Date dateStart, Date dateEnd) {
        Utilities util = new Utilities();
        Log.e("module ", module);
        String APIUrl;
        String MODULE_REVIEWS = "submissions_completed";
        String MODULE_FEEDBACKS = "student_feedbacks";
        String reviewsAPIUrl = "https://review-api.udacity.com/api/v1/me/submissions/completed";
        String feedbacksAPIUrl =  "https://review-api.udacity.com/api/v1/me/student_feedbacks";
        if ( module.equals(MODULE_REVIEWS)){
            APIUrl = reviewsAPIUrl;
        }else if ( module.equals(MODULE_FEEDBACKS)){
            APIUrl = feedbacksAPIUrl;
        }else{
            return "error: empty module argument";
        }
        Log.e("APIUrl ", APIUrl);

        ArrayList<String> params = new ArrayList<>();
        boolean hasParams = false;
        if (dateStart != null) {
            Log.e("dateStart ", "true");
            params.add("start_date=" + util.getDateAsString(dateStart,DATE_FORMAT, "UTC"));
            hasParams = true;
        }
        if (dateEnd != null) {
            params.add("end_date=" + util.getDateAsString(dateEnd, DATE_FORMAT, "UTC"));
            hasParams = true;
        }
        Log.e("url ", APIUrl);
        if (hasParams) {
            String UrlParams = util.buildStringFromArray(params, "&");
            if (UrlParams != null) {
                Log.e("urlParams ", UrlParams);
                Log.e("reviewApiUrl ", reviewsAPIUrl);
                APIUrl = APIUrl + "?" + UrlParams;
                Log.e("API URL ", APIUrl);
            }
        }else{
            Log.e("params ", "no params");
        }

        return httpConnect(APIUrl);
    }

    /**
     * httpConnect
     * This method handles communicating with the API and converting the input stream into a string
     * @return a json string to be used in a parsing method
     */
    private String httpConnect(String APIurl) {
        String results = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            // Build the URL
            Uri builtUri = Uri.parse(APIurl).buildUpon().build();
            URL url = new URL(builtUri.toString());
            // Establish the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("Authorization", apiKey);
            urlConnection.addRequestProperty("Content-Length", "0");
            urlConnection.addRequestProperty("Accept", "application/json");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                results = null;
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging ->  + "\n"
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                results = null;
            }
            results = buffer.toString();
        } catch (IOException v) {
            results = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i("json ", results);
        return results;
    }


}