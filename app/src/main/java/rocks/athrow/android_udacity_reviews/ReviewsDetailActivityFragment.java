package rocks.athrow.android_udacity_reviews;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReviewsDetailActivityFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_REVIEW_ID = "id";
    public static final String ARG_PROJECT_NAME = "project_name";
    public static final String ARG_USER_NAME = "user_name";
    public static final String ARG_RESULT = "result";
    public static final String ARG_ASSIGNED_AT = "assigned_at";
    public static final String ARG_COMPLETED_AT= "completed_at";
    public static final String ARG_ELAPSED_TIME = "elapsed_time";
    public static final String ARG_ARCHIVE_URL = "archive_url";
    public static final String ARG_FILENAME = "filename";
    private String projectName;
    private String reviewId;
    private String userName;
    private String assginedAt;
    private String completedAt;
    private String result;
    private String elapsedTime;
    private String archiveUrl;
    private String fileName;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReviewsDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the review data from the intent extras
        projectName = getArguments().getString(ARG_PROJECT_NAME);
        reviewId = getArguments().getString(ARG_REVIEW_ID);
        userName = getArguments().getString(ARG_USER_NAME);
        assginedAt = getArguments().getString(ARG_ASSIGNED_AT);
        completedAt = getArguments().getString(ARG_COMPLETED_AT);
        result = getArguments().getString(ARG_RESULT);
        elapsedTime = getArguments().getString(ARG_ELAPSED_TIME);
        archiveUrl = getArguments().getString(ARG_ARCHIVE_URL);
        fileName = getArguments().getString(ARG_FILENAME);

        if (getArguments().containsKey(ARG_PROJECT_NAME)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.


            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(projectName);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews_detail, container, false);


        // Get the views
        TextView reviewIdView = (TextView) rootView.findViewById(R.id.review_detail_review_id);
        TextView userNameView = (TextView) rootView.findViewById(R.id.review_detail_user_name);
        TextView assignedAtView = (TextView) rootView.findViewById(R.id.review_detail_assigned_at);
        TextView completedAtView = (TextView) rootView.findViewById(R.id.review_detail_completed_at);
        TextView resultView = (TextView) rootView.findViewById(R.id.review_detail_result);
        TextView elapsedTimeView = (TextView) rootView.findViewById(R.id.review_detail_elapsed_time);
        TextView filenameView = (TextView) rootView.findViewById(R.id.review_detail_archive_filename);
        // Set the views
        reviewIdView.setText(reviewId);
        userNameView.setText(userName);
        assignedAtView.setText(assginedAt);
        completedAtView.setText(completedAt);
        resultView.setText(result);
        elapsedTimeView.setText(elapsedTime);
        filenameView.setText(fileName);

        if (result.equals("passed")) {
            resultView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.badge_passed) );
        } else if (result.equals("failed")) {
            resultView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.badge_failed) );
        } else {
            resultView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.badge_cant_review) );

        }


        return rootView;
    }
}
