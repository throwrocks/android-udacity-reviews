package rocks.athrow.android_udacity_reviews.data;

import rocks.athrow.android_udacity_reviews.util.Utilities;

/**
 * SummaryObject
 * A class to store data objects for the ReportsFragment
 * Types: Reviews or Project
 * The Reviews object holds the grand total for the report
 * The Project object holds the sub total for a project
 * Created by josel on 8/20/2016.
 */
public class SummaryObject {
    String type;
    int reviewsCount;
    Number reviewsRevenue;
    Number elapsedTime;

    String projectName;
    int projectCount;
    Number projectRevenue;
    long projectHours;

    public SummaryObject(String type, String projectName, int projectCount,
                         Number projectRevenue, long projectHours){
        this.type = type;
        this.projectName = projectName;
        this.projectCount = projectCount;
        this.projectRevenue = projectRevenue;
        this.projectHours = projectHours;
    }

    public  SummaryObject(String type, int reviewsCount, Number reviewSummary, Number elapsedTime){
        this.type = type;
        this.reviewsCount = reviewsCount;
        this.reviewsRevenue = reviewSummary;
        this.elapsedTime = elapsedTime;
    }

    public String getReviewsCount() {
        return Integer.toString(reviewsCount);
    }

    public String getReviewsRevenue() {
        return Utilities.formatCurrency(reviewsRevenue.doubleValue());
    }

    public String getElapsedTime() {
        return Utilities.millisecondsToHours(elapsedTime.longValue());
    }

    public String getProjectName(){
        return this.projectName;
    }

    public String getProjectCount() {
        return Integer.toString(projectCount);
    }

    public String getProjectRevenue() {
        return Utilities.formatCurrency(projectRevenue.doubleValue());
    }

    public String getProjectHours() {
        return Utilities.millisecondsToHours(this.projectHours);
        //return String.valueOf(this.projectHours);
    }
}