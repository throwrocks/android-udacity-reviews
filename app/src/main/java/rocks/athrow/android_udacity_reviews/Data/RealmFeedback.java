package rocks.athrow.android_udacity_reviews.Data;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by josel on 7/6/2016.
 */
public class RealmFeedback extends RealmObject {
    @PrimaryKey
    int id;
    int rubric_id;
    int submission_id;
    int user_id;
    int rating;
    String body;
    Date created_at;
    Date updated_at;
    int grader_id;
    String read_at;
    int project_id;
    String project_name;

    public RealmFeedback() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRubric_id() {
        return rubric_id;
    }

    public void setRubric_id(int rubric_id) {
        this.rubric_id = rubric_id;
    }

    public int getSubmission_id() {
        return submission_id;
    }

    public void setSubmission_id(int submission_id) {
        this.submission_id = submission_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getGrader_id() {
        return grader_id;
    }

    public void setGrader_id(int grader_id) {
        this.grader_id = grader_id;
    }

    public String getRead_at() {
        return read_at;
    }

    public void setRead_at(String read_at) {
        this.read_at = read_at;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}
