package rocks.athrow.android_udacity_reviews.data;

/**
 * ProjectSummary
 * This class is used to store rows of project summary data
 */
public class SummaryProject {
    String projectName;
    int projectCount;
    double projectRevenue;
    public SummaryProject(String projectName, int projectCount, double projectRevenue){
        this.projectName = projectName;
        this.projectCount = projectCount;
        this.projectRevenue = projectRevenue;
    }
    public String getProjectName(){
        return this.projectName;
    }

    public int getProjectCount() {
        return projectCount;
    }

    public double getProjectRevenue() {
        return projectRevenue;
    }
}