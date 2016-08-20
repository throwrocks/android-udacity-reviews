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
    String projectName;
    int projectCount;
    Number projectRevenue;

    public SummaryObject(String type, String projectName, int projectCount, Number projectRevenue){
        this.type = type;
        this.projectName = projectName;
        this.projectCount = projectCount;
        this.projectRevenue = projectRevenue;
    }

    public  SummaryObject(String type, int reviewsCount, Number reviewSummary){
        this.type = type;
        this.reviewsCount = reviewsCount;
        this.reviewsRevenue = reviewSummary;
    }

    public String getReviewsCount() {
        return Integer.toString(reviewsCount);
    }

    public String getReviewsRevenue() {
        return Utilities.formatCurrency(reviewsRevenue.doubleValue());
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

}