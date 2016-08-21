package rocks.athrow.android_udacity_reviews.data;

import rocks.athrow.android_udacity_reviews.util.Utilities;

/**
 * SummaryObject
 * A class to store data objects for the ReportsFragment
 * Types: Reviews or Project
 * The Reviews object holds the grand total for the report
 * The Project object holds the sub total for a reports_project
 * Created by josel on 8/20/2016.
 */
public class SummaryObject {
    // The type of SummaryObject (reviews or project)
    String type;
    // Reviews Summary fields
    int reviewsCount;
    Number reviewsRevenue;
    Number elapsedTime;
    // Project Summary fields
    String projectName;
    int projectCount;
    Number projectRevenue;
    long projectHours;

    /**
     * SummaryObject
     *
     * @param type           a reviews type SummaryObject
     * @param reviewsCount   the total count of reviews
     * @param reviewsRevenue the total revenue for the reviews
     * @param elapsedTime    the total time between assignment and completion, in millis
     */
    public SummaryObject(String type, int reviewsCount, Number reviewsRevenue, Number elapsedTime) {
        this.type = type;
        this.reviewsCount = reviewsCount;
        this.reviewsRevenue = reviewsRevenue;
        this.elapsedTime = elapsedTime;
    }

    /**
     * getReviewsCount
     *
     * @return the number of reviews in a String for display purposes
     */
    public String getReviewsCount() {
        return Integer.toString(reviewsCount);
    }

    /**
     * getReviewsRevenue
     *
     * @return the total revenue formatted in currency for display purposes
     */
    public String getReviewsRevenue() {
        return Utilities.formatCurrency(reviewsRevenue.doubleValue());
    }

    /**
     * getElapsedTime
     *
     * @return the total time spent from assignments to completions in hh:mm format
     */
    public String getElapsedTime() {
        return Utilities.millisecondsToHours(elapsedTime.longValue());
    }

    /**
     * SummaryObject
     *
     * @param type           a project type SummaryObject
     * @param projectName    the name of the project
     * @param projectCount   the total count for the project
     * @param projectRevenue the total revenue for the project
     * @param projectHours   the total hours for the project
     */
    public SummaryObject(String type, String projectName, int projectCount,
                         Number projectRevenue, long projectHours) {
        this.type = type;
        this.projectName = projectName;
        this.projectCount = projectCount;
        this.projectRevenue = projectRevenue;
        this.projectHours = projectHours;
    }

    /**
     * getProjectName
     *
     * @return the name of the project
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * getProjectCount
     *
     * @return the total count of project in String for display purposes
     */
    public String getProjectCount() {
        return Integer.toString(projectCount);
    }

    /**
     * getProjectRevenue
     *
     * @return the total revenue formatted in currency for display purposes
     */
    public String getProjectRevenue() {
        return Utilities.formatCurrency(projectRevenue.doubleValue());
    }

    /**
     * getpPojectHours
     *
     * @return the total time spent from assignments to completions in hh:mm format
     */
    public String getProjectHours() {
        return Utilities.millisecondsToHours(this.projectHours);
    }
}