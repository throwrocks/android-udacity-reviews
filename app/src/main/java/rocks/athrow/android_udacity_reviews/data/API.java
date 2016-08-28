package rocks.athrow.android_udacity_reviews.data;

import java.util.ArrayList;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

    /**
     * callReviewsAPI
     * @param APIKey the API key string

     * @return the API response in a string
     */
    public static String callReviewsAPI(String APIKey, String dateStart, String dateEnd){
        String APIUrl = "https://review-api.udacity.com/api/v1/me/submissions/completed";
        ArrayList<String> paramsArray = new ArrayList<>();
        boolean hasParams = false;
        if (dateStart != null && !dateStart.equals("")) {
            paramsArray.add("start_date=" + dateStart);
            hasParams = true;
        }
        if (dateEnd != null && !dateEnd.equals("")) {
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
     * callFeedbacksAPI
     * @param APIKey the API key string
     *
     * @return the API response in a string
     */
    public static String callFeedbacksAPI(String APIKey, String dateStart, String dateEnd){
        String APIUrl = "https://review-api.udacity.com/api/v1/me/student_feedbacks";
        ArrayList<String> paramsArray = new ArrayList<>();
        boolean hasParams = false;
        if (dateStart != null && !dateStart.equals("")) {
            paramsArray.add("start_date=" + dateStart);
            hasParams = true;
        }
        if (dateEnd != null && !dateEnd.equals("")) {
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
            Uri builtUri = Uri.parse(APIurl).buildUpon().build();
            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("Authorization", APIKey);
            urlConnection.addRequestProperty("Content-Length", "0");
            urlConnection.addRequestProperty("Accept", "application/json");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                results = null;
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (buffer.length() == 0) {
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