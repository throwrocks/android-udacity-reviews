package rocks.athrow.android_udacity_reviews.data;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONParser
 * A class to parse Review data
 * There are two types of Review entities: Reviews and Feedbacks
 * Created by joselopez on 7/5/16.
 */
public final class JSONParser {

    private JSONParser() {
        throw new AssertionError("No JSONParser instances for you!");
    }

    /**
     * parseReviews
     *
     * @param reviewsJSON a string of results from submissions completed API call
     * @return a ContentValues array with the results as ContentValues
     */
    public static ContentValues[] parseReviews(String reviewsJSON) {
        ContentValues[] contentValues = null;
        if (reviewsJSON != null) {
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
                    int id = reviewRecord.getInt(RealmReview.fields.id);
                    int project_id = reviewRecord.getInt(RealmReview.fields.project_id);
                    int rubric_id = reviewRecord.getInt(RealmReview.fields.rubric_id);
                    int user_id = reviewRecord.getInt(RealmReview.fields.id);
                    int grader_id = reviewRecord.getInt(RealmReview.fields.grader_id);
                    reviewValues.put(RealmReview.fields.id, id);
                    reviewValues.put(RealmReview.fields.project_id, project_id);
                    reviewValues.put(RealmReview.fields.rubric_id, rubric_id);
                    reviewValues.put(RealmReview.fields.user_id, user_id);
                    reviewValues.put(RealmReview.fields.grader_id, grader_id);
                    //----------------------------------------------------------------------------------
                    // User
                    //----------------------------------------------------------------------------------
                    JSONObject userNode = reviewRecord.getJSONObject(RealmReview.fields.user);
                    String user_name = userNode.getString(RealmReview.fields.name);
                    reviewValues.put(RealmReview.fields.user_name, user_name);
                    //----------------------------------------------------------------------------------
                    // Dates
                    //----------------------------------------------------------------------------------
                    String created_at = reviewRecord.getString(RealmReview.fields.created_at);
                    String updated_at = reviewRecord.getString(RealmReview.fields.updated_at);
                    String assigned_at = reviewRecord.getString(RealmReview.fields.assigned_at);
                    String completed_at = reviewRecord.getString(RealmReview.fields.completed_at);
                    reviewValues.put(RealmReview.fields.completed_at, completed_at);
                    reviewValues.put(RealmReview.fields.created_at, created_at);
                    reviewValues.put(RealmReview.fields.updated_at, updated_at);
                    reviewValues.put(RealmReview.fields.assigned_at, assigned_at);
                    //----------------------------------------------------------------------------------
                    // Submission data
                    //----------------------------------------------------------------------------------
                    double price = reviewRecord.getDouble(RealmReview.fields.price);
                    String repo_url = reviewRecord.getString(RealmReview.fields.repo_url);
                    String commit_sha = reviewRecord.getString(RealmReview.fields.commit_sha);
                    String archive_url = reviewRecord.getString(RealmReview.fields.archive_url);
                    String udacity_key = reviewRecord.getString(RealmReview.fields.udacity_key);
                    String held_at = reviewRecord.getString(RealmReview.fields.held_at);
                    String student_notes = reviewRecord.getString(RealmReview.fields.notes);
                    reviewValues.put(RealmReview.fields.price, price);
                    reviewValues.put(RealmReview.fields.repo_url, repo_url);
                    reviewValues.put(RealmReview.fields.commit_sha, commit_sha);
                    reviewValues.put(RealmReview.fields.archive_url, archive_url);
                    reviewValues.put(RealmReview.fields.udacity_key, udacity_key);
                    reviewValues.put(RealmReview.fields.held_at, held_at);
                    reviewValues.put(RealmReview.fields.notes, student_notes);
                    //----------------------------------------------------------------------------------
                    // Status and Result
                    //----------------------------------------------------------------------------------
                    String status = reviewRecord.getString(RealmReview.fields.status);
                    String result = reviewRecord.getString(RealmReview.fields.result);
                    String status_reason = reviewRecord.getString(RealmReview.fields.status_reason);
                    String result_reason = reviewRecord.getString(RealmReview.fields.result_reason);
                    reviewValues.put(RealmReview.fields.status, status);
                    reviewValues.put(RealmReview.fields.result, result);
                    reviewValues.put(RealmReview.fields.status_reason, status_reason);
                    reviewValues.put(RealmReview.fields.result_reason, result_reason);
                    //----------------------------------------------------------------------------------
                    // Project data
                    // The commented out variables are referencing elements that are not in the JSON
                    // response. It seems they were deprecated but not remoevd from the documentation
                    //----------------------------------------------------------------------------------
                    JSONObject projectNode = reviewRecord.getJSONObject(RealmReview.fields.project);
                    String project_name = projectNode.getString(RealmReview.fields.name);
                    reviewValues.put(RealmReview.fields.project_name, project_name);
                    //----------------------------------------------------------------------------------
                    // Add the ContentValues to the Array
                    //----------------------------------------------------------------------------------
                    contentValues[i] = reviewValues;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        ContentValues[] contentValues = null;
        if (feedbacksJSON != null) {
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
        }
        return contentValues;
    }

}