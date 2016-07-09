package rocks.athrow.android_udacity_reviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import rocks.athrow.android_udacity_reviews.Data.RealmFeedback;
import rocks.athrow.android_udacity_reviews.Data.RealmReview;

public class ReviewsDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String reviewId = intent.getStringExtra(ReviewsDetailActivityFragment.ARG_REVIEW_ID);
            String projectName = intent.getStringExtra(ReviewsDetailActivityFragment.ARG_PROJECT_NAME);
            String userName =  intent.getStringExtra(ReviewsDetailActivityFragment.ARG_USER_NAME);
            String result = intent.getStringExtra(ReviewsDetailActivityFragment.ARG_RESULT);
            String completedAt = intent.getStringExtra(ReviewsDetailActivityFragment.ARG_COMPLETED_AT);
            String assignedAt = intent.getStringExtra(ReviewsDetailActivityFragment.ARG_ASSIGNED_AT);
            String elapsedTime = intent.getStringExtra(ReviewsDetailActivityFragment.ARG_ELAPSED_TIME);
            String archiveUrl =  intent.getStringExtra(ReviewsDetailActivityFragment.ARG_ARCHIVE_URL);
            String filename = intent.getStringExtra(ReviewsDetailActivityFragment.ARG_FILENAME);
            String studentNotes = intent.getStringExtra(ReviewsDetailActivityFragment.ARG_STUDENT_NOTES);
            int rating = intent.getIntExtra(ReviewsDetailActivityFragment.ARG_RATING, 0);
            // Get the review body
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
            Realm.setDefaultConfiguration(realmConfig);
            Realm realm = Realm.getDefaultInstance();
            RealmQuery<RealmFeedback> reviewsQuery = realm.where(RealmFeedback.class);
            reviewsQuery.equalTo("submission_id", Integer.parseInt(reviewId));
            RealmResults<RealmFeedback> reviewsResult = reviewsQuery.findAll();
            String studentFeedback = null;
            if (reviewsResult.size() > 0) {
                studentFeedback = reviewsResult.get(0).getBody();
            }
            // Create a bundle
            Bundle arguments = new Bundle();
            // Put the arguments in a bundle and pass them to the fragment
            arguments.putString( ReviewsDetailActivityFragment.ARG_REVIEW_ID, reviewId);
            arguments.putString( ReviewsDetailActivityFragment.ARG_PROJECT_NAME, projectName);
            arguments.putString( ReviewsDetailActivityFragment.ARG_USER_NAME, userName);
            arguments.putString( ReviewsDetailActivityFragment.ARG_RESULT, result);
            arguments.putString( ReviewsDetailActivityFragment.ARG_COMPLETED_AT, completedAt);
            arguments.putString( ReviewsDetailActivityFragment.ARG_ASSIGNED_AT, assignedAt);
            arguments.putString( ReviewsDetailActivityFragment.ARG_ELAPSED_TIME, elapsedTime);
            arguments.putString( ReviewsDetailActivityFragment.ARG_ARCHIVE_URL, archiveUrl);
            arguments.putString( ReviewsDetailActivityFragment.ARG_FILENAME, filename);
            arguments.putString( ReviewsDetailActivityFragment.ARG_STUDENT_NOTES, studentNotes);
            arguments.putInt( ReviewsDetailActivityFragment.ARG_RATING, rating);
            arguments.putString(ReviewsDetailActivityFragment.ARG_STUDENT_FEEDBACK, studentFeedback);
            // Set the fragment
            ReviewsDetailActivityFragment fragment = new ReviewsDetailActivityFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.review_detail_container, fragment)
                    .commit();
        }
    }

}
