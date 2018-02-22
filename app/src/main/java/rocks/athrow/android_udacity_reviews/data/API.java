package rocks.athrow.android_udacity_reviews.data;

import android.net.Uri;
import android.util.Log;

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

    /**
     * callReviewsAPI
     *
     * @param APIKey the API key string
     * @return the API response in a string
     */
    public static APIResponse callReviewsAPI(String APIKey, String dateStart, String dateEnd) {
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
        return httpConnect(APIKey, APIUrl, "GET", "");
    }

    /**
     * callFeedbacksAPI
     *
     * @param APIKey the API key string
     * @return the API response in a string
     */
    public static APIResponse callFeedbacksAPI(String APIKey, String dateStart, String dateEnd) {
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
        return httpConnect(APIKey, APIUrl, "GET", "");
    }

    /**
     * httpConnect
     * This method handles communicating with the API and converting the input stream into a string
     *
     * @param apiKey        the API key
     * @param apiUrl        the request url
     * @param requestMethod the request's method (GET, PUT, DELETE)
     * @param requestBody   the request's body (optional)
     * @return a json string to be used in a parsing method
     */
    private static APIResponse httpConnect(String apiKey, String apiUrl, String requestMethod, String requestBody) {
        APIResponse apiResponse = new APIResponse();
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            Uri builtUri = Uri.parse(apiUrl).buildUpon().build();
            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(requestMethod);
            urlConnection.addRequestProperty("Body", requestBody);
            urlConnection.addRequestProperty("Authorization", apiKey);
            urlConnection.addRequestProperty("Content-Length", "0");
            urlConnection.addRequestProperty("Accept", "application/json");
            urlConnection.connect();
            apiResponse.setResponseCode(urlConnection.getResponseCode());
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return apiResponse;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                return apiResponse;
        }
            Log.i("API", "response string: " + buffer.toString());
            apiResponse.setResponseText(buffer.toString());
        } catch (IOException v) {
            apiResponse.setResponseText(v.toString());
            return apiResponse;
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
        return apiResponse;
    }
}