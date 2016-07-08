package rocks.athrow.android_udacity_reviews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import rocks.athrow.android_udacity_reviews.Data.RealmFeedback;
import rocks.athrow.android_udacity_reviews.Data.RealmReview;

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
    public static final String ARG_COMPLETED_AT = "completed_at";
    public static final String ARG_ELAPSED_TIME = "elapsed_time";
    public static final String ARG_ARCHIVE_URL = "archive_url";
    public static final String ARG_FILENAME = "filename";
    public static final String ARG_STUDENT_NOTES = "notes";
    public static final String ARG_RATING = "rating";
    private String projectName;
    private String reviewId;
    private String userName;
    private String assginedAt;
    private String completedAt;
    private String result;
    private String elapsedTime;
    private String reviewUrl;
    private String archiveUrl;
    private String fileName;
    private String studentNotes;
    private int rating;
    private String feedbackString;

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
        reviewUrl = "https://review.udacity.com/#!/reviews/" + reviewId;
        archiveUrl = getArguments().getString(ARG_ARCHIVE_URL);
        studentNotes = getArguments().getString(ARG_STUDENT_NOTES);
        rating = getArguments().getInt(ARG_RATING);
        fileName = getArguments().getString(ARG_FILENAME);

        // Get the feedback
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<RealmFeedback> query = realm.where(RealmFeedback.class);
        // Add query conditions:
        query.equalTo("submission_id", Integer.parseInt(reviewId));
        RealmResults<RealmFeedback> feedbacks = query.findAll().sort("created_at", Sort.DESCENDING);
        realm.commitTransaction();
        if ( feedbacks.size() > 0 ){
            feedbackString = feedbacks.get(0).getBody();
        }


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
        Button reviewButton = (Button) rootView.findViewById(R.id.review_details_project_review);
        TextView studentNotesView = (TextView) rootView.findViewById(R.id.review_detail_student_notes);
        LinearLayout viewRatingBox = (LinearLayout) rootView.findViewById(R.id.review_rating_box);
        TextView viewReviewNone = (TextView) rootView.findViewById(R.id.review_rating_none);
        ImageView viewReviewStar1  = (ImageView) rootView.findViewById(R.id.review_rating_star1);
        ImageView viewReviewStar2 = (ImageView) rootView.findViewById(R.id.review_rating_star2);
        ImageView viewReviewStar3 = (ImageView) rootView.findViewById(R.id.review_rating_star3);
        ImageView  viewReviewStar4 = (ImageView) rootView.findViewById(R.id.review_rating_star4);
        ImageView  viewReviewStar5 = (ImageView) rootView.findViewById(R.id.review_rating_star5);
        TextView viewStudentFeedback = (TextView) rootView.findViewById(R.id.review_detail_student_feedback);
        // Set the views
        reviewIdView.setText(reviewId);
        userNameView.setText(userName);
        assignedAtView.setText(assginedAt);
        completedAtView.setText(completedAt);
        resultView.setText(result);
        elapsedTimeView.setText(elapsedTime);
        reviewButton.setText(reviewUrl);
        viewStudentFeedback.setText(feedbackString);


        if ( rating == 0 ){
            viewReviewNone.setText("Not Rated");
            viewReviewNone.setVisibility(View.VISIBLE);
            viewRatingBox.setVisibility(View.GONE);
        }else if ( rating > 0) {
            viewReviewNone.setVisibility(View.GONE);
            viewRatingBox.setVisibility(View.VISIBLE);
            Drawable starFilled = ContextCompat.getDrawable(getContext(), R.drawable.icon_star_filled);
            if (rating == 1) {
                viewReviewStar1.setBackground(starFilled);
            } else if (rating == 2) {
                viewReviewStar1.setBackground(starFilled);
                viewReviewStar2.setBackground(starFilled);
            } else if (rating == 3) {
                viewReviewStar1.setBackground(starFilled);
                viewReviewStar2.setBackground(starFilled);
                viewReviewStar3.setBackground(starFilled);
            } else if (rating == 4) {
                viewReviewStar1.setBackground(starFilled);
                viewReviewStar2.setBackground(starFilled);
                viewReviewStar3.setBackground(starFilled);
                viewReviewStar4.setBackground(starFilled);
            } else if (rating == 5) {
                viewReviewStar1.setBackground(starFilled);
                viewReviewStar2.setBackground(starFilled);
                viewReviewStar3.setBackground(starFilled);
                viewReviewStar4.setBackground(starFilled);
                viewReviewStar5.setBackground(starFilled);
            }
        }


        if (studentNotes.equals("null")) {
            studentNotes = "No notes provided";
        }
        studentNotesView.setText(studentNotes);


        if (result.equals("passed")) {
            resultView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.badge_passed));
        } else if (result.equals("failed")) {
            resultView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.badge_failed));
        } else {
            resultView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.badge_cant_review));

        }

        // Set the button to open the file
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = reviewUrl;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        return rootView;
    }
}
