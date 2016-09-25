package rocks.athrow.android_udacity_reviews.data;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import rocks.athrow.android_udacity_reviews.util.Utilities;

/**
 * Created by joselopez on 7/5/16.
 */
public class RealmReview extends RealmObject {
    //----------------------------------------------------------------------------------
    // IDs
    //----------------------------------------------------------------------------------
    @PrimaryKey
    private int id;
    private int project_id;
    private int rubric_id;
    private int user_id;
    private int grader_id;
    //----------------------------------------------------------------------------------
    // User
    //----------------------------------------------------------------------------------
    private String user_name;
    //----------------------------------------------------------------------------------
    // Dates
    //----------------------------------------------------------------------------------
    private Date created_at;
    private Date updated_at;
    private Date assigned_at;
    private Date completed_at;
    private long elapsed_time;
    //----------------------------------------------------------------------------------
    // Submission data
    //----------------------------------------------------------------------------------
    private double price;
    private String repo_url;
    private String commit_sha;
    private String archive_url;
    private String udacity_key;
    private String held_at;
    private String notes;
    //----------------------------------------------------------------------------------
    // Submission data
    //----------------------------------------------------------------------------------
    private String status;
    private String result;
    private String status_reason;
    private String result_reason;
    //----------------------------------------------------------------------------------
    // Project data
    //----------------------------------------------------------------------------------
    @Index
    private String project_name;
    private String project_hash_tag;
    private String project_required_skills;
    private int project_awaiting_review_count;
    private Boolean project_visible;
    private int project_audit_rubric_id;
    //----------------------------------------------------------------------------------
    // Added / Not in the API
    //----------------------------------------------------------------------------------
    private int feedback_rating;

    public RealmReview(){
    }


    public void setElapsed_time(long elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public String getElapsedTimeDisplay(){
        return Utilities.elapsedTimeDisplay(this.assigned_at, this.completed_at);
    }

    public Date getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(Date completed_at) {
        this.completed_at = completed_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getRubric_id() {
        return rubric_id;
    }

    public void setRubric_id(int rubric_id) {
        this.rubric_id = rubric_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGrader_id() {
        return grader_id;
    }

    public void setGrader_id(int grader_id) {
        this.grader_id = grader_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public Date getAssigned_at() {
        return assigned_at;
    }

    public void setAssigned_at(Date assigned_at) {
        this.assigned_at = assigned_at;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRepo_url() {
        return repo_url;
    }

    public void setRepo_url(String repo_url) {
        this.repo_url = repo_url;
    }

    public String getCommit_sha() {
        return commit_sha;
    }

    public void setCommit_sha(String commit_sha) {
        this.commit_sha = commit_sha;
    }

    public String getArchive_url() {
        return archive_url;
    }

    public void setArchive_url(String archive_url) {
        this.archive_url = archive_url;
    }

    public String getUdacity_key() {
        return udacity_key;
    }

    public void setUdacity_key(String udacity_key) {
        this.udacity_key = udacity_key;
    }

    public String getHeld_at() {
        return held_at;
    }

    public void setHeld_at(String held_at) {
        this.held_at = held_at;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus_reason() {
        return status_reason;
    }

    public void setStatus_reason(String status_reason) {
        this.status_reason = status_reason;
    }

    public String getResult_reason() {
        return result_reason;
    }

    public void setResult_reason(String result_reason) {
        this.result_reason = result_reason;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_hash_tag() {
        return project_hash_tag;
    }

    public void setProject_hash_tag(String project_hash_tag) {
        this.project_hash_tag = project_hash_tag;
    }

    public String getProject_required_skills() {
        return project_required_skills;
    }

    public void setProject_required_skills(String project_required_skills) {
        this.project_required_skills = project_required_skills;
    }

    public int getProject_awaiting_review_count() {
        return project_awaiting_review_count;
    }

    public void setProject_awaiting_review_count(int project_awaiting_review_count) {
        this.project_awaiting_review_count = project_awaiting_review_count;
    }

    public Boolean getProject_visible() {
        return project_visible;
    }

    public void setProject_visible(Boolean project_visible) {
        this.project_visible = project_visible;
    }

    public int getProject_audit_rubric_id() {
        return project_audit_rubric_id;
    }

    public void setProject_audit_rubric_id(int project_audit_rubric_id) {
        this.project_audit_rubric_id = project_audit_rubric_id;
    }

    public int getFeedback_rating() {
        return feedback_rating;
    }

    public void setFeedback_rating(int feedback_rating) {
        this.feedback_rating = feedback_rating;
    }

}
