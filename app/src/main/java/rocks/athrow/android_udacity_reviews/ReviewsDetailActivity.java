package rocks.athrow.android_udacity_reviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Intent intent = getIntent();

            Bundle arguments = new Bundle();
            // Put the arguments in a bundle and pass them to the fragment
            arguments.putString(
                    ReviewsDetailActivityFragment.ARG_PROJECT_NAME,
                    intent.getStringExtra(ReviewsDetailActivityFragment.ARG_PROJECT_NAME)
            );
            arguments.putString(
                    ReviewsDetailActivityFragment.ARG_RESULT,
                    intent.getStringExtra(ReviewsDetailActivityFragment.ARG_RESULT)
            );
            arguments.putString(
                    ReviewsDetailActivityFragment.ARG_COMPLETED_AT,
                    intent.getStringExtra(ReviewsDetailActivityFragment.ARG_COMPLETED_AT)
            );


            ReviewsDetailActivityFragment fragment = new ReviewsDetailActivityFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.review_detail_container, fragment)
                    .commit();
        }
    }

}
