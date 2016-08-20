package rocks.athrow.android_udacity_reviews.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.interfaces.OnReportQueryCompleted;

/**
 * ReportQueryTask
 * Created by josel on 8/20/2016.
 */
public class ReportQueryTask extends AsyncTask<String, Void, ArrayList<SummaryObject>> {
    private Context context;
    private final static String FIELD_COMPLETED_DATE = "completed_at";
    private final static String FIELD_PRICE = "price";
    public OnReportQueryCompleted listener = null;
    Date date1;
    Date date2;

    public ReportQueryTask(Context context, OnReportQueryCompleted listener, Date date1, Date date2) {
        this.context = context;
        this.listener = listener;
        this.date1 = date1;
        this.date2 = date2;
    }

    @Override
    protected ArrayList<SummaryObject> doInBackground(String... String) {
        // Create an ArrayList of SummaryObjects
        ArrayList<SummaryObject> summaryObjects = new ArrayList<SummaryObject>();
        // Get a Realm instance
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        // Add the query conditions
        RealmQuery<RealmReview> query = realm.where(RealmReview.class);
        query.between(FIELD_COMPLETED_DATE, date1, date2);
        // Execute the query
        RealmResults<RealmReview> results = query.findAll();
        realm.commitTransaction();
        // Get the SummaryReviews attributes
        int count = results.size();
        // If nothing found return null
        if (count == 0) {
            return null;
        }
        Number revenue = results.sum(FIELD_PRICE);
        // Store the reviews summary
        SummaryObject summaryReviews = new SummaryObject("reviews", count, revenue);
        summaryObjects.add(summaryReviews);
        // Get the distinct project names
        RealmResults<RealmReview> projects = query.distinct("project_name");
        Log.e("projects ", "" + projects.size());
        int totalProjectsCount = projects.size();
        if (totalProjectsCount > 0) {
            int i = 0;
            while (i < totalProjectsCount) {
                RealmReview project = projects.get(i);
                String projectName = project.getProject_name();
                // Being a new Realm transaction
                realm.beginTransaction();
                RealmQuery<RealmReview> projectQuery = realm.where(RealmReview.class);
                // Set the query conditions
                projectQuery.equalTo("project_name", projectName).between(FIELD_COMPLETED_DATE, date1, date2);
                // Execute the query
                RealmResults<RealmReview> projectResults = projectQuery.findAll();
                realm.commitTransaction();
                // Get the project's revenue
                Number projectRevenue = projectResults.sum(FIELD_PRICE);
                int projectsCount = projectResults.size();
                // Store the project's summary
                SummaryObject summaryProject = new SummaryObject("project", projectName, projectsCount, projectRevenue);
                Log.e("project: ", projectResults.size() + " " + projectName + " " + projectRevenue);
                summaryObjects.add(summaryProject);
                i++;
            }
            realm.close();
        }
        return summaryObjects;
    }

    @Override
    protected void onPostExecute(ArrayList<SummaryObject> summaryObjects) {
        listener.OnReportQueryCompleted(summaryObjects);
    }
}
