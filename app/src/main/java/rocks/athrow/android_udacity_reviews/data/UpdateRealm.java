package rocks.athrow.android_udacity_reviews.data;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.util.Utilities;

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
    public void updateReviews(ContentValues[] reviews) {
        for (ContentValues value : reviews) {
            Log.e("value ", "" + value);
            int findId = value.getAsInteger("id");
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
            Realm.setDefaultConfiguration(realmConfig);
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.beginTransaction();
                // Build the query looking at all users:
                RealmQuery<RealmReview> query = realm.where(RealmReview.class);
                // Add query conditions:
                query.equalTo("id", findId);
                // Execute the query:
                RealmResults<RealmReview> results = query.findAll();
                int resultsCount = results.size();
                // If record doesn't exist,. create it
                if (resultsCount == 0) {
                    // Create new Review object
                    RealmReview newReview = new RealmReview();
                    //----------------------------------------------------------------------------------
                    // IDs
                    //----------------------------------------------------------------------------------
                    int id = value.getAsInteger("id");
                    int project_id = value.getAsInteger("id");
                    int rubric_id = value.getAsInteger("rubric_id");
                    int user_id = value.getAsInteger("id");
                    int grader_id = value.getAsInteger("grader_id");
                    newReview.setId(id);
                    newReview.setProject_id(project_id);
                    newReview.setRubric_id(rubric_id);
                    newReview.setUser_id(user_id);
                    newReview.setGrader_id(grader_id);
                    //----------------------------------------------------------------------------------
                    // User
                    //----------------------------------------------------------------------------------
                    String user_name = value.getAsString("user_name");
                    newReview.setUser_name(user_name);
                    //----------------------------------------------------------------------------------
                    // Dates
                    //----------------------------------------------------------------------------------
                    Date created_at = Utilities.getStringAsDate(value.getAsString("created_at"), DATE_UTC, TIMEZONE_UTC);
                    Date updated_at = Utilities.getStringAsDate(value.getAsString("updated_at"), DATE_UTC, TIMEZONE_UTC);
                    Date assigned_at = Utilities.getStringAsDate(value.getAsString("assigned_at"), DATE_UTC, TIMEZONE_UTC);
                    Date completed_at = Utilities.getStringAsDate(value.getAsString("completed_at"), DATE_UTC, TIMEZONE_UTC);
                    long elapsed_time = Utilities.elapsedMilliseconds(assigned_at, completed_at);
                    newReview.setCreated_at(created_at);
                    newReview.setUpdated_at(updated_at);
                    newReview.setAssigned_at(assigned_at);
                    newReview.setCompleted_at(completed_at);
                    newReview.setElapsed_time(elapsed_time);
                    //----------------------------------------------------------------------------------
                    // Submission data
                    //----------------------------------------------------------------------------------
                    double price = value.getAsDouble("price");
                    String repo_url = value.getAsString("repo_url");
                    String commit_sha = value.getAsString("commit_sha");
                    String archive_url = value.getAsString("archive_url");
                    String udacity_key = value.getAsString("udacity_key");
                    String held_at = value.getAsString("held_at");
                    String student_notes = value.getAsString("notes");
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
                    String status = value.getAsString("status");
                    String result = value.getAsString("result");
                    String status_reason = value.getAsString("status_reason");
                    String result_reason = value.getAsString("result_reason");
                    newReview.setStatus(status);
                    newReview.setResult(result);
                    newReview.setStatus_reason(status_reason);
                    newReview.setResult_reason(result_reason);
                    //----------------------------------------------------------------------------------
                    // Project data
                    //----------------------------------------------------------------------------------
                    String project_name = value.getAsString("project_name");
                    newReview.setProject_name(project_name);
                    //----------------------------------------------------------------------------------
                    // + Copy to Realm
                    //----------------------------------------------------------------------------------
                    realm.copyToRealmOrUpdate(newReview);
                }
                realm.commitTransaction();
                realm.close();
            } catch (Exception e) {
                realm.cancelTransaction();
            }
        }
    }

    /**
     * updateFeedbacks
     *
     * @param feedbacks an array of ContentValues built from the JSONParser
     */
    public void updateFeedbacks(ContentValues[] feedbacks) {
        int feedbacksCount = feedbacks.length;
        Log.e("feedbacks Qty ", "" + feedbacksCount);
        for (ContentValues value : feedbacks) {
            int findId = value.getAsInteger("id");
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
            Realm.setDefaultConfiguration(realmConfig);
            Realm realm = Realm.getDefaultInstance();
            try {
                realm.beginTransaction();
                // Build the query looking at all users:
                RealmQuery<RealmFeedback> query = realm.where(RealmFeedback.class);
                // Add query conditions:
                query.equalTo("id", findId);
                // Execute the query:
                RealmResults<RealmFeedback> results = query.findAll();
                int resultsCount = results.size();
                // If record doesn't exist,. create it
                if (resultsCount == 0) {
                    // Create new Review object
                    RealmFeedback newFeedback = new RealmFeedback();
                    //----------------------------------------------------------------------------------
                    // IDs
                    //----------------------------------------------------------------------------------
                    int id = value.getAsInteger("id");
                    int rubric_id = value.getAsInteger("rubric_id");
                    int submission_id = value.getAsInteger("submission_id");
                    int user_id = value.getAsInteger("id");
                    int grader_id = value.getAsInteger("grader_id");
                    //int project_id = value.getAsInteger("id");
                    String project_name = value.getAsString("project_name");
                    newFeedback.setId(id);
                    newFeedback.setRubric_id(rubric_id);
                    newFeedback.setSubmission_id(submission_id);
                    newFeedback.setUser_id(user_id);
                    newFeedback.setGrader_id(grader_id);
                    //newFeedback.setProject_id(project_id);
                    newFeedback.setProject_name(project_name);
                    //----------------------------------------------------------------------------------
                    // Feedback information
                    //----------------------------------------------------------------------------------
                    int rating = value.getAsInteger("rating");
                    String body = value.getAsString("body");
                    newFeedback.setRating(rating);
                    newFeedback.setBody(body);
                    //----------------------------------------------------------------------------------
                    // Dates
                    //----------------------------------------------------------------------------------
                    Date created_at = Utilities.getStringAsDate(value.getAsString("created_at"), DATE_UTC, TIMEZONE_UTC);
                    Date updated_at = Utilities.getStringAsDate(value.getAsString("updated_at"), DATE_UTC, TIMEZONE_UTC);
                    newFeedback.setCreated_at(created_at);
                    newFeedback.setUpdated_at(updated_at);
                    //----------------------------------------------------------------------------------
                    // + Copy to Realm
                    //----------------------------------------------------------------------------------
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

                }
                realm.commitTransaction();
                realm.close();
            } catch (Exception e) {
                realm.cancelTransaction();
            }

        }
    }

}
