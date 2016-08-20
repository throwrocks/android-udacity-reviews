package rocks.athrow.android_udacity_reviews.data;

import rocks.athrow.android_udacity_reviews.util.Utilities;

/**
 * ReportSummary
 * This class is used to store the total reviews summary
 */
public class SummaryReport{
    int reviewsCount;
    Number reviewsRevenue;
    public  SummaryReport(int reviewsCount, Number reviewSummary){
        this.reviewsCount = reviewsCount;
        this.reviewsRevenue = reviewSummary;
    }

    public String getReviewsCount() {
        return Integer.toString(reviewsCount);
    }

    public String getReviewSummary() {
        return Utilities.formatCurrency(reviewsRevenue.doubleValue());
    }
}
