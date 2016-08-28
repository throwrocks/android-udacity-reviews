package rocks.athrow.android_udacity_reviews.data;

import android.content.ContentValues;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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

    private static final String MODULE_REVIEWS = "submissions_completed";
    private static final String MODULE_FEEDBACKS = "student_feedbacks";
    private static final String REVIEWS_API_URL = "https://review-api.udacity.com/api/v1/me/submissions/completed";
    private static final String FEEDBACKS_API_URL = "https://review-api.udacity.com/api/v1/me/student_feedbacks";

    /**
     * callAPI
     *
     * @param params a ContentValues object containing the parameters
     * @return the API response in a string
     */
    public static String callAPI(String APIKey, ContentValues params) {
        String APIUrl;
        String module = params.getAsString("module");
        switch (module) {
            case MODULE_REVIEWS:
                APIUrl = REVIEWS_API_URL;
                break;
            case MODULE_FEEDBACKS:
                APIUrl = FEEDBACKS_API_URL;
                break;
            default:
                return "error: empty module argument";
        }
        ArrayList<String> paramsArray = new ArrayList<>();
        String dateStart = params.getAsString("date_start");
        String dateEnd = params.getAsString("date_end");
        boolean hasParams = false;
        if (!dateStart.equals("")) {
            paramsArray.add("start_date=" + dateStart);
            hasParams = true;
        }
        if (!dateEnd.equals("")) {
            paramsArray.add("end_date=" + dateEnd);
            hasParams = true;
        }
        if (hasParams) {
            String UrlParams = Utilities.buildStringFromArray(paramsArray, "&");
            if (UrlParams != null) {
                APIUrl = APIUrl + "?" + UrlParams;
            }
        }
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
            urlConnection.addRequestProperty("Authorization", APIKey);
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