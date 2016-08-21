package rocks.athrow.android_udacity_reviews.data;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by joselopez on 7/5/16.
 */
final class JSONParser {

    private JSONParser(){
        throw new AssertionError("No JSONParser instances for you!");
    }

    /**
     * parseReviews
     *
     * @param reviewsJSON a string of results from submissions completed API call
     * @return a ContentValues array with the results as ContentValues
     */
    public static ContentValues[] parseReviews(String reviewsJSON) {
        ContentValues[] contentValues = new ContentValues[0];

        try {
            JSONArray reviewsArray = new JSONArray(reviewsJSON);
            int reviewsQty = reviewsArray.length();
            contentValues = new ContentValues[reviewsQty];

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
                String student_notes = reviewRecord.getString("notes");
                reviewValues.put("price", price);
                reviewValues.put("repo_url", repo_url);
                reviewValues.put("commit_sha", commit_sha);
                reviewValues.put("archive_url", archive_url);
                reviewValues.put("udacity_key", udacity_key);
                reviewValues.put("held_at", held_at);
                reviewValues.put("notes", student_notes);
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
                reviewValues.put("project_name", project_name);
                //----------------------------------------------------------------------------------
                // Add the ContentValues to the Array
                //----------------------------------------------------------------------------------
                contentValues[i] = reviewValues;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contentValues;
    }

    /**
     * parseFeedbacks
     *
     * @param feedbacksJSON a string of results from student feedbacks API call
     * @return a ContentValues array with the results as ContentValues
     */
    public static ContentValues[] parseFeedbacks(String feedbacksJSON) {
        ContentValues[] contentValues = new ContentValues[0];
        try {
            JSONArray feedbacksArray = new JSONArray(feedbacksJSON);
            int feedbacksQty = feedbacksArray.length();

            contentValues = new ContentValues[feedbacksQty];
            for (int i = 0; i < feedbacksQty; i++) {
                JSONObject reviewRecord = feedbacksArray.getJSONObject(i);
                // Create a ContentValues object
                ContentValues reviewValues = new ContentValues();
                // Parse the individual data elements and build the ContentValues
                //----------------------------------------------------------------------------------
                // IDs
                //----------------------------------------------------------------------------------
                int id = reviewRecord.getInt("id");
                int rubric_id = reviewRecord.getInt("rubric_id");
                int submission_id = reviewRecord.getInt("submission_id");
                int user_id = reviewRecord.getInt("id");
                int grader_id = reviewRecord.getInt("grader_id");
                //int project_id = reviewRecord.getInt("project_id");
                JSONObject projectNode = reviewRecord.getJSONObject("project");
                String project_name = projectNode.getString("name");
                reviewValues.put("id", id);
                reviewValues.put("submission_id", submission_id);
                reviewValues.put("rubric_id", rubric_id);
                reviewValues.put("user_id", user_id);
                reviewValues.put("grader_id", grader_id);
                //reviewValues.put("project_id", project_id);
                reviewValues.put("project_name", project_name);
                //----------------------------------------------------------------------------------
                // Feedback information
                //----------------------------------------------------------------------------------
                int rating = reviewRecord.getInt("rating");
                String body = reviewRecord.getString("body");
                reviewValues.put("rating", rating);
                reviewValues.put("body", body);
                //----------------------------------------------------------------------------------
                // Dates
                //----------------------------------------------------------------------------------
                String created_at = reviewRecord.getString("created_at");
                String updated_at = reviewRecord.getString("updated_at");
                reviewValues.put("created_at", created_at);
                reviewValues.put("updated_at", updated_at);
                //----------------------------------------------------------------------------------
                // Add the ContentValues to the Array
                //----------------------------------------------------------------------------------
                contentValues[i] = reviewValues;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contentValues;
    }

}