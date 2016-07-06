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

import rocks.athrow.android_udacity_reviews.BuildConfig;
import rocks.athrow.android_udacity_reviews.Utilities;

/**
 * Created by joselopez on 3/10/16.
 */
class API {

    Context mContext;

    private String reviewsAPIUrl = "https://review-api.udacity.com/api/v1/me/submissions/completed";
    private static final String apiKey = BuildConfig.UDACITY_REVIEWER_API_KEY;

    // Constructor
    public API(Context context) {
        this.mContext = context;
    }

    public String callAPI(String module, String dateStart, String dateEnd) {
        ArrayList<String> params = new ArrayList<>();
        boolean hasParams = false;
        if (dateStart != null) {
            params.add("start_date=" + dateStart);
            hasParams = true;
        }
        if (dateEnd != null) {
            params.add("end_date=" + dateEnd);
            hasParams = true;
        }
        if (hasParams) {
            Utilities util = new Utilities();
            String UrlParams = util.buildStringFromArray(params, "&");
            if (UrlParams != null) {
                Log.e("urlParams ", UrlParams);
                Log.e("reviewApiUrl ", reviewsAPIUrl);
                reviewsAPIUrl = reviewsAPIUrl + "?" + UrlParams;
                Log.e("params ", reviewsAPIUrl);
            }
        }else{
            Log.e("params ", "no params");
        }

        return httpConnect(reviewsAPIUrl);
    }

    /**
     * callAPI
     *
     * @return
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
        return results;
    }


}