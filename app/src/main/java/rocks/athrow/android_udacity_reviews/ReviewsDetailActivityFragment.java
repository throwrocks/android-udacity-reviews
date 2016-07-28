package rocks.athrow.android_udacity_reviews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import rocks.athrow.android_udacity_reviews.Data.RealmFeedback;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReviewsDetailActivityFragment extends Fragment {


    public static final String ARG_REVIEW_ID = "id";
    public static final String ARG_PROJECT_NAME = "project_name";
    public static final String ARG_PRICE = "price";
    public static final String ARG_USER_NAME = "user_name";
    public static final String ARG_RESULT = "result";
    public static final String ARG_ASSIGNED_AT = "assigned_at";
    public static final String ARG_COMPLETED_AT = "completed_at";
    public static final String ARG_ELAPSED_TIME = "elapsed_time";
    public static final String ARG_ARCHIVE_URL = "archive_url";
    public static final String ARG_FILENAME = "filename";
    public static final String ARG_STUDENT_NOTES = "notes";
    public static final String ARG_RATING = "rating";
    public static final String ARG_STUDENT_FEEDBACK = "student_feedback";
    private static final String UDACITY_REVIEWS_URL = "https://review.udacity.com/#!/reviews/";
    private String projectName;
    private double price;
    private String priceDisplay;
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
    private String studentFeedback;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReviewsDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utilities util = new Utilities();
        // Get the review data from the intent extras
        projectName = getArguments().getString(ARG_PROJECT_NAME);
        price = getArguments().getDouble(ARG_PRICE);
        priceDisplay = util.formatCurrency(price);
        reviewId = getArguments().getString(ARG_REVIEW_ID);
        userName = getArguments().getString(ARG_USER_NAME);
        assginedAt = getArguments().getString(ARG_ASSIGNED_AT);
        completedAt = getArguments().getString(ARG_COMPLETED_AT);
        result = getArguments().getString(ARG_RESULT);
        elapsedTime = getArguments().getString(ARG_ELAPSED_TIME);
        reviewUrl = UDACITY_REVIEWS_URL + reviewId;
        archiveUrl = getArguments().getString(ARG_ARCHIVE_URL);
        studentNotes = getArguments().getString(ARG_STUDENT_NOTES);
        rating = getArguments().getInt(ARG_RATING);
        fileName = getArguments().getString(ARG_FILENAME);
        studentFeedback = getArguments().getString(ARG_STUDENT_FEEDBACK);

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
            studentFeedback = feedbacks.get(0).getBody();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews_detail, container, false);


        // Get the views
        TextView projectNameView = (TextView) rootView.findViewById(R.id.review_detail_review_project_name);
        TextView userNameView = (TextView) rootView.findViewById(R.id.review_detail_user_name);
        TextView reviewRateView = (TextView) rootView.findViewById(R.id.review_detail_review_rate);
        TextView reviewIdView = (TextView) rootView.findViewById(R.id.review_detail_review_id);
        TextView assignedAtView = (TextView) rootView.findViewById(R.id.review_detail_assigned_at);
        TextView completedAtView = (TextView) rootView.findViewById(R.id.review_detail_completed_at);
        TextView resultView = (TextView) rootView.findViewById(R.id.review_detail_result);
        TextView elapsedTimeView = (TextView) rootView.findViewById(R.id.review_detail_elapsed_time);
        Button reviewButton = (Button) rootView.findViewById(R.id.review_details_project_review);
        TextView studentNotesView = (TextView) rootView.findViewById(R.id.review_detail_student_notes);
        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.rating_bar);
        TextView viewReviewNone = (TextView) rootView.findViewById(R.id.review_rating_none);
        TextView viewStudentFeedback = (TextView) rootView.findViewById(R.id.review_detail_student_feedback);
        // Set the views
        projectNameView.setText(projectName);
        reviewRateView.setText(priceDisplay);
        reviewIdView.setText(reviewId);
        userNameView.setText(userName);
        assignedAtView.setText(assginedAt);
        completedAtView.setText(completedAt);
        resultView.setText(result);
        elapsedTimeView.setText(elapsedTime);
        reviewButton.setText(reviewUrl);


        Log.d("price",  "" + price);
        if ( rating == 0 ){
            viewReviewNone.setText("Not Rated");
            viewReviewNone.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.GONE);
        }else if ( rating > 0) {
            viewReviewNone.setVisibility(View.GONE);
            ratingBar.setVisibility(View.VISIBLE);
            ratingBar.setRating(rating);
        }


        if (studentNotes.equals("") || studentNotes.equals("null")) {
            studentNotes = "No notes provided";
        }
        studentNotesView.setText(studentNotes);

        if ( studentFeedback == null){
            studentFeedback = "No feedback provided";
        }else if ( studentFeedback.equals("") || studentFeedback.equals("null")){
            studentFeedback = "No feedback provided";
        }
        viewStudentFeedback.setText(studentFeedback);


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
