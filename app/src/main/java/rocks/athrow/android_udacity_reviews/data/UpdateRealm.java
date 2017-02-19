package rocks.athrow.android_udacity_reviews.data;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.util.Utilities;

import static android.R.attr.value;

/**
 * UpdateRealm
 * A class to handle updating the Realm database
 * Created by joselopez on 7/5/16.
 */
public class UpdateRealm {
    private Context mContext;
    private final static String DATE_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private final static String TIMEZONE_UTC = "UTC";

    public UpdateRealm(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * updateReviews
     *
     * @param reviews an array of ContentValues built from the JSONParser
     */
    public void updateReviews(JSONArray reviews) {
        int reviewsQty = reviews.length();
        if (reviewsQty == 0) {
            return;
        }
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        for (int i = 0; i < reviewsQty; i++) {
            Log.e("value ", "" + value);
            try {
                JSONObject reviewRecord = reviews.getJSONObject(i);
                RealmReview newReview = new RealmReview();
                realm.beginTransaction();
                //----------------------------------------------------------------------------------
                // IDs
                //----------------------------------------------------------------------------------
                int id = reviewRecord.getInt(RealmReview.fields.id);
                int project_id = reviewRecord.getInt(RealmReview.fields.project_id);
                int rubric_id = reviewRecord.getInt(RealmReview.fields.rubric_id);
                int user_id = reviewRecord.getInt(RealmReview.fields.id);
                int grader_id = reviewRecord.getInt(RealmReview.fields.grader_id);
                newReview.setId(id);
                newReview.setProject_id(project_id);
                newReview.setRubric_id(rubric_id);
                newReview.setUser_id(user_id);
                newReview.setGrader_id(grader_id);
                //----------------------------------------------------------------------------------
                // User
                //----------------------------------------------------------------------------------
                JSONObject userNode = reviewRecord.getJSONObject(RealmReview.fields.user);
                String user_name = userNode.getString(RealmReview.fields.name);
                newReview.setUser_name(user_name);
                //----------------------------------------------------------------------------------
                // Dates
                //----------------------------------------------------------------------------------
                String created_at = reviewRecord.getString(RealmReview.fields.created_at);
                String updated_at = reviewRecord.getString(RealmReview.fields.updated_at);
                String assigned_at = reviewRecord.getString(RealmReview.fields.assigned_at);
                String completed_at = reviewRecord.getString(RealmReview.fields.completed_at);
                Date created_at_date = Utilities.getStringAsDate(created_at, DATE_UTC, TIMEZONE_UTC);
                Date updated_at_date = Utilities.getStringAsDate(updated_at, DATE_UTC, TIMEZONE_UTC);
                Date assigned_at_date = Utilities.getStringAsDate(assigned_at, DATE_UTC, TIMEZONE_UTC);
                Date completed_at_date = Utilities.getStringAsDate(completed_at, DATE_UTC, TIMEZONE_UTC);
                long elapsed_time = Utilities.elapsedMilliseconds(assigned_at_date, completed_at_date);
                newReview.setCreated_at(created_at_date);
                newReview.setUpdated_at(updated_at_date);
                newReview.setAssigned_at(assigned_at_date);
                newReview.setCompleted_at(completed_at_date);
                newReview.setElapsed_time(elapsed_time);
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
                newReview.setPrice(price);
                newReview.setRepo_url(repo_url);
                newReview.setCommit_sha(commit_sha);
                newReview.setArchive_url(archive_url);
                newReview.setUdacity_key(udacity_key);
                newReview.setHeld_at(held_at);
                newReview.setNotes(student_notes);
                //----------------------------------------------------------------------------------
                // Status and Result
                //----------------------------------------------------------------------------------
                String status = reviewRecord.getString(RealmReview.fields.status);
                String result = reviewRecord.getString(RealmReview.fields.result);
                String status_reason = reviewRecord.getString(RealmReview.fields.status_reason);
                String result_reason = reviewRecord.getString(RealmReview.fields.result_reason);
                newReview.setStatus(status);
                newReview.setResult(result);
                newReview.setStatus_reason(status_reason);
                newReview.setResult_reason(result_reason);
                //----------------------------------------------------------------------------------
                // Project data
                // The commented out variables are referencing elements that are not in the JSON
                // response. It seems they were deprecated but not remoevd from the documentation
                //----------------------------------------------------------------------------------
                JSONObject projectNode = reviewRecord.getJSONObject(RealmReview.fields.project);
                String project_name = projectNode.getString(RealmReview.fields.name);
                newReview.setProject_name(project_name);
                realm.copyToRealmOrUpdate(newReview);
                realm.commitTransaction();
            } catch (JSONException e) {
                e.printStackTrace();
                realm.cancelTransaction();
            }
        }
        realm.close();
    }

    /**
     * updateFeedbacks
     *
     * @param feedbacks an array of ContentValues built from the JSONParser
     */
    public void updateFeedbacks(JSONArray feedbacks) {
        int feedbacksQty = feedbacks.length();
        if (feedbacksQty == 0) {
            return;
        }
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        for (int i = 0; i < feedbacksQty; i++) {
            Log.e("value ", "" + value);
            try {
                JSONObject feedbackRecord = feedbacks.getJSONObject(i);
                RealmFeedback newFeedback = new RealmFeedback();
                realm.beginTransaction();
                //----------------------------------------------------------------------------------
                // IDs
                //----------------------------------------------------------------------------------
                int id = feedbackRecord.getInt("id");
                int rubric_id = feedbackRecord.getInt("rubric_id");
                int submission_id = feedbackRecord.getInt("submission_id");
                int user_id = feedbackRecord.getInt("id");
                int grader_id = feedbackRecord.getInt("grader_id");
                JSONObject projectNode = feedbackRecord.getJSONObject("project");
                String project_name = projectNode.getString("name");
                newFeedback.setId(id);
                newFeedback.setRubric_id(rubric_id);
                newFeedback.setSubmission_id(submission_id);
                newFeedback.setUser_id(user_id);
                newFeedback.setGrader_id(grader_id);
                newFeedback.setProject_name(project_name);
                //----------------------------------------------------------------------------------
                // Feedback information
                //----------------------------------------------------------------------------------
                int rating = feedbackRecord.getInt("rating");
                String body = feedbackRecord.getString("body");
                newFeedback.setRating(rating);
                newFeedback.setBody(body);
                //----------------------------------------------------------------------------------
                // Dates
                //----------------------------------------------------------------------------------
                String created_at = feedbackRecord.getString("created_at");
                String updated_at = feedbackRecord.getString("updated_at");
                Date created_at_date = Utilities.getStringAsDate(created_at, DATE_UTC, TIMEZONE_UTC);
                Date updated_at_date = Utilities.getStringAsDate(updated_at, DATE_UTC, TIMEZONE_UTC);
                newFeedback.setCreated_at(created_at_date);
                newFeedback.setUpdated_at(updated_at_date);
                realm.copyToRealmOrUpdate(newFeedback);
                //----------------------------------------------------------------------------------
                // Update the reports_project rating
                //----------------------------------------------------------------------------------
                RealmQuery<RealmReview> reviewsQuery = realm.where(RealmReview.class);
                reviewsQuery.equalTo("id", submission_id);
                RealmResults<RealmReview> reviewsResult = reviewsQuery.findAll();
                if (reviewsResult.size() > 0) {
                    reviewsResult.get(0).setFeedback_rating(rating);
                }
                realm.commitTransaction();
            }catch (JSONException e){
                realm.cancelTransaction();
                e.printStackTrace();
            }
        }
        realm.close();
    }

}
