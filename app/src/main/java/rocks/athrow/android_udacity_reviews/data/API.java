package rocks.athrow.android_udacity_reviews.data;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import rocks.athrow.android_udacity_reviews.BuildConfig;
import rocks.athrow.android_udacity_reviews.util.Utilities;

/**
 * API
 * This class manages the connection to the Udacity API
 * Created by joselopez on 3/10/16.
 */
public final class API {

    private API() {
        throw new AssertionError("No API instances for you!");
    }

    //private static final String apiKey = BuildConfig.UDACITY_REVIEWER_API_KEY;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String MODULE_REVIEWS = "submissions_completed";
    private static final String MODULE_FEEDBACKS = "student_feedbacks";
    private static final String REVIEWS_API_URL = "https://review-api.udacity.com/api/v1/me/submissions/completed";
    private static final String FEEDBACKS_API_URL = "https://review-api.udacity.com/api/v1/me/student_feedbacks";

    /**
     * callAPI
     *
     * @param module    the API module (supported: submissions_completed, student_feedbacks)
     * @param dateStart the start date to retrieve results from
     * @param dateEnd   the end date to retrieve result to
     * @return the API response in a string
     */
    public static String callAPI(String APIKey, String module, Date dateStart, Date dateEnd) {
        String APIUrl;
        if (module.equals(MODULE_REVIEWS)) {
            APIUrl = REVIEWS_API_URL;
        } else if (module.equals(MODULE_FEEDBACKS)) {
            APIUrl = FEEDBACKS_API_URL;
        } else {
            return "error: empty module argument";
        }
        ArrayList<String> params = new ArrayList<>();
        boolean hasParams = false;
        if (dateStart != null) {
            params.add("start_date=" + Utilities.getDateAsString(dateStart, DATE_FORMAT, "UTC"));
            hasParams = true;
        }
        if (dateEnd != null) {
            params.add("end_date=" + Utilities.getDateAsString(dateEnd, DATE_FORMAT, "UTC"));
            hasParams = true;
        }
        if (hasParams) {
            String UrlParams = Utilities.buildStringFromArray(params, "&");
            if (UrlParams != null) {
                APIUrl = APIUrl + "?" + UrlParams;
            }
        }
        // TODO: Handle invalid parameter

        return httpConnect(APIKey, APIUrl);
    }

    /**
     * httpConnect
     * This method handles communicating with the API and converting the input stream into a string
     *
     * @return a json string to be used in a parsing method
     */
    private static String httpConnect(String APIKey, String APIurl) {
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
            urlConnection.addRequestProperty("Authorization",APIKey );
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
        return results;
    }
}