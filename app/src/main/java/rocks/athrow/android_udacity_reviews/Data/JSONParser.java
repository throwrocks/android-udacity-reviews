package rocks.athrow.android_udacity_reviews.Data;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rocks.athrow.android_udacity_reviews.Data.API;

/**
 * Created by joselopez on 7/5/16.
 */
public class JSONParser {
    private static final String LOG_TAG = API.class.getSimpleName();
    ContentValues[] mContentValues;
    Context mContext;

    // Constructor
    public JSONParser(Context context){
        this.mContext = context;
    }

    public ContentValues[] parseReviews(String reviewsJSON) {
        try {
            JSONArray reviewsArray = new JSONArray(reviewsJSON);
            int reviewsQty = reviewsArray.length();
            Log.e("qty ", Integer.toString(reviewsQty));
            mContentValues = new ContentValues[reviewsQty];
            for (int i = 0; i < reviewsQty; i++) {
                JSONObject reviewRecord = reviewsArray.getJSONObject(i);
                // Create a ContentValues object
                ContentValues reviewValues = new ContentValues();
                // Parse the individual data elements and build the ContentValues
                //----------------------------------------------------------------------------------
                // IDs
                //----------------------------------------------------------------------------------
                int id = reviewRecord.getInt("id");
                int project_id = reviewRecord.getInt("project_id");
                int rubric_id = reviewRecord.getInt("rubric_id");
                int user_id = reviewRecord.getInt("id");
                int grader_id = reviewRecord.getInt("grader_id");
                reviewValues.put("id", id);
                reviewValues.put("project_id", project_id);
                reviewValues.put("rubric_id", rubric_id);
                reviewValues.put("user_id", user_id);
                reviewValues.put("grader_id", grader_id);
                //----------------------------------------------------------------------------------
                // User
                //----------------------------------------------------------------------------------
                JSONObject userNode = reviewRecord.getJSONObject("user");
                String user_name = userNode.getString("name");
                reviewValues.put("user_name", user_name);
                //----------------------------------------------------------------------------------
                // Dates
                //----------------------------------------------------------------------------------
                String created_at = reviewRecord.getString("created_at");
                String updated_at = reviewRecord.getString("updated_at");
                String assigned_at = reviewRecord.getString("assigned_at");
                String completed_at = reviewRecord.getString("completed_at");
                reviewValues.put("completed_at", completed_at);
                reviewValues.put("created_at", created_at);
                reviewValues.put("updated_at", updated_at);
                reviewValues.put("assigned_at", assigned_at);
                //----------------------------------------------------------------------------------
                // Submission data
                //----------------------------------------------------------------------------------
                double price = reviewRecord.getDouble("price");
                String repo_url = reviewRecord.getString("repo_url");
                String commit_sha = reviewRecord.getString("commit_sha");
                String archive_url = reviewRecord.getString("archive_url");
                String udacity_key = reviewRecord.getString("udacity_key");
                String held_at = reviewRecord.getString("held_at");
                reviewValues.put("price", price);
                reviewValues.put("repo_url", repo_url);
                reviewValues.put("commit_sha", commit_sha);
                reviewValues.put("archive_url", archive_url);
                reviewValues.put("udacity_key", udacity_key);
                reviewValues.put("held_at", held_at);
                //----------------------------------------------------------------------------------
                // Status and Result
                //----------------------------------------------------------------------------------
                String status = reviewRecord.getString("status");
                String result = reviewRecord.getString("result");
                String status_reason = reviewRecord.getString("status_reason");
                String result_reason = reviewRecord.getString("result_reason");
                reviewValues.put("status", status);
                reviewValues.put("result", result);
                reviewValues.put("status_reason", status_reason);
                reviewValues.put("result_reason", result_reason);
                //----------------------------------------------------------------------------------
                // Project data
                // The commented out variables are referencing elements that are not in the JSON
                // response. It seems they were deprecated but not remoevd from the documentation
                //----------------------------------------------------------------------------------
                JSONObject projectNode = reviewRecord.getJSONObject("project");
                String project_name = projectNode.getString("name");
                //String project_required_skills = projectNode.getString("required_skills");
                //int project_awaiting_review_count = projectNode.getInt("awaiting_review_count");
                //String project_hash_tag = projectNode.getString("hashtag");
                //Boolean project_visible = projectNode.getBoolean("visible");
                //int project_audit_rubric_id = projectNode.getInt("audit_rubric_id");
                reviewValues.put("project_name", project_name);
                //reviewValues.put("project_hash_tag", project_hash_tag);
                //reviewValues.put("project_required_skills", project_required_skills);
                //reviewValues.put("project_awaiting_review_count", project_awaiting_review_count);
                //reviewValues.put("project_visible", project_visible);
                //reviewValues.put("project_audit_rubric_id", project_audit_rubric_id);
                Log.e(LOG_TAG, "" + reviewValues.size());
                //----------------------------------------------------------------------------------
                // Add the ContentValues to the Array
                //----------------------------------------------------------------------------------
                mContentValues[i] = reviewValues;
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return mContentValues;
    }

}
