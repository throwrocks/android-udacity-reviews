package rocks.athrow.android_udacity_reviews.Data;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by joselopez on 7/5/16.
 */
public class UpdateRealm {
    private Context mContext;
    public UpdateRealm(Context mContext) {
        this.mContext = mContext;
    }

    public void updateReviews(ContentValues[] reviews) {
        int reviewsCount = reviews.length;
        Log.e("updateReviews Qty ","" + reviewsCount);
        for (ContentValues value: reviews) {
            //Log.e("value ","" + value.size());
            int findId = value.getAsInteger("id");
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(mContext).build();
            Realm.setDefaultConfiguration(realmConfig);
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            // Build the query looking at all users:
            RealmQuery<Review> query = realm.where(Review.class);
            // Add query conditions:
            query.equalTo("id", findId);

            // Execute the query:
            RealmResults<Review> results = query.findAll();
            int resultsCount = results.size();
            // If record doesn't exist,. create it
            if (resultsCount == 0 ) {
                // Create new Review object
                Review newReview = new Review();
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
                String created_at = value.getAsString("created_at");
                String updated_at = value.getAsString("updated_at");
                String assigned_at = value.getAsString("assigned_at");
                String completed_at = value.getAsString("completed_at");
                newReview.setCreated_at(created_at);
                newReview.setUpdated_at(updated_at);
                newReview.setAssigned_at(assigned_at);
                newReview.setCompleted_at(completed_at);
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

        }
    }

}
