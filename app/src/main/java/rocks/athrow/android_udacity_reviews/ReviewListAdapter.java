package rocks.athrow.android_udacity_reviews;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.Data.RealmFeedback;
import rocks.athrow.android_udacity_reviews.Data.RealmReview;
import rocks.athrow.android_udacity_reviews.RealmAdapter.RealmRecyclerViewAdapter;

/**
 * ReviewListAdapter
 * Binds the data from the Review Realm Objects to the
 * ReviewListFragmentActivity RecyclerView
 */
public class ReviewListAdapter extends RealmRecyclerViewAdapter<RealmReview> {
    private Context context;

    private final static String DATE_TIME_DISPLAY = "MM/dd/yy h:mm a";
    private final static String DATE_DISPLAY = "MM/dd/yy";
    private class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewReviewItem;
        public TextView viewReviewId;
        public TextView viewProjectName;
        public TextView viewCompletedAt;
        public TextView viewUserName;
        public TextView viewResult;
        public TextView viewFilename;
        public LinearLayout viewRatingBox;
        public TextView viewReviewNone;
        public ImageView viewReviewStar1;
        public ImageView viewReviewStar2;
        public ImageView viewReviewStar3;
        public ImageView viewReviewStar4;
        public ImageView viewReviewStar5;

        public ViewHolder(View view) {
            super(view);
            viewReviewItem = (LinearLayout) view.findViewById(R.id.review_item);
            viewProjectName = (TextView) view.findViewById(R.id.review_item_project_name);
            viewCompletedAt = (TextView) view.findViewById(R.id.review_item_completed_at);
            viewUserName = (TextView) view.findViewById(R.id.review_user_name);
            viewUserName = (TextView) view.findViewById(R.id.review_user_name);
            viewResult = (TextView) view.findViewById(R.id.review_result);
            viewReviewId = (TextView) view.findViewById(R.id.review_id);
            viewRatingBox = (LinearLayout) view.findViewById(R.id.review_rating_box);
            viewReviewNone = (TextView) view.findViewById(R.id.review_rating_none);
            viewReviewStar1 = (ImageView) view.findViewById(R.id.review_rating_star1);
            viewReviewStar2 = (ImageView) view.findViewById(R.id.review_rating_star2);
            viewReviewStar3 = (ImageView) view.findViewById(R.id.review_rating_star3);
            viewReviewStar4 = (ImageView) view.findViewById(R.id.review_rating_star4);
            viewReviewStar5 = (ImageView) view.findViewById(R.id.review_rating_star5);

        }
    }

    public ReviewListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View reviewListRecyclerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);
        return new ViewHolder(reviewListRecyclerView);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder reviewListRecyclerView = (ViewHolder) viewHolder;
        // Get the review record
        RealmReview reviewRecord = getItem(position);
        //------------------------------------------------------------------------------------------
        // Set the review variables
        //------------------------------------------------------------------------------------------
        final int id = reviewRecord.getId();
        final String idString = Integer.toString(id);
        final String projectName = reviewRecord.getProject_name();
        final Date assignedAt = reviewRecord.getAssigned_at();
        final Date completedAt = reviewRecord.getCompleted_at();
        final String userName = reviewRecord.getUser_name();
        final String result = reviewRecord.getResult();
        final String archiveUrl = reviewRecord.getArchive_url();
        final String studentNotes = reviewRecord.getNotes();
        //------------------------------------------------------------------------------------------
        // Format the dates for the List and Detail Views
        //------------------------------------------------------------------------------------------
        Utilities util = new Utilities();
        final String completedAtListDisplay = util.getDateAsString(completedAt, DATE_DISPLAY, null);
        final String completedAtDetailDisplay = util.getDateAsString(completedAt, DATE_TIME_DISPLAY, null) ;
        final String assignedAtDetailDisplay = util.getDateAsString(assignedAt,DATE_TIME_DISPLAY, null);
        //------------------------------------------------------------------------------------------
        // Get the elapsed time between start/end
        //------------------------------------------------------------------------------------------
        // TODO: Store this value in the database?
        final String elapsedTime = util.elapsedTime(assignedAt, completedAt);
        //------------------------------------------------------------------------------------------
        // Get the filename from the archive url
        //------------------------------------------------------------------------------------------
        String[] urlItems = util.stringSplit(archiveUrl,"/");
        int urtlItemsCount = urlItems.length;
        final String fileName = urlItems[urtlItemsCount-1];
        //------------------------------------------------------------------------------------------
        // Get the rating
        //------------------------------------------------------------------------------------------
        final int rating = reviewRecord.getFeedback_rating();
        //------------------------------------------------------------------------------------------
        // Set the views
        //------------------------------------------------------------------------------------------
        reviewListRecyclerView.viewReviewId.setText(idString);
        reviewListRecyclerView.viewProjectName.setText(projectName);
        reviewListRecyclerView.viewCompletedAt.setText(completedAtListDisplay);
        reviewListRecyclerView.viewUserName.setText(userName);
        reviewListRecyclerView.viewUserName.setText(userName);

        if ( rating == 0 ){
            reviewListRecyclerView.viewReviewNone.setText("Not Rated");
            reviewListRecyclerView.viewReviewNone.setVisibility(View.VISIBLE);
            reviewListRecyclerView.viewRatingBox.setVisibility(View.GONE);
        }else if ( rating > 0) {
            reviewListRecyclerView.viewReviewNone.setVisibility(View.GONE);
            reviewListRecyclerView.viewRatingBox.setVisibility(View.VISIBLE);
            Drawable starFilled = ContextCompat.getDrawable(context, R.drawable.icon_star_filled);
            if (rating == 1) {
                reviewListRecyclerView.viewReviewStar1.setBackground(starFilled);
            } else if (rating == 2) {
                reviewListRecyclerView.viewReviewStar1.setBackground(starFilled);
                reviewListRecyclerView.viewReviewStar2.setBackground(starFilled);
            } else if (rating == 3) {
                reviewListRecyclerView.viewReviewStar1.setBackground(starFilled);
                reviewListRecyclerView.viewReviewStar2.setBackground(starFilled);
                reviewListRecyclerView.viewReviewStar3.setBackground(starFilled);
            } else if (rating == 4) {
                reviewListRecyclerView.viewReviewStar1.setBackground(starFilled);
                reviewListRecyclerView.viewReviewStar2.setBackground(starFilled);
                reviewListRecyclerView.viewReviewStar3.setBackground(starFilled);
                reviewListRecyclerView.viewReviewStar4.setBackground(starFilled);
            } else if (rating == 5) {
                reviewListRecyclerView.viewReviewStar1.setBackground(starFilled);
                reviewListRecyclerView.viewReviewStar2.setBackground(starFilled);
                reviewListRecyclerView.viewReviewStar3.setBackground(starFilled);
                reviewListRecyclerView.viewReviewStar4.setBackground(starFilled);
                reviewListRecyclerView.viewReviewStar5.setBackground(starFilled);
            }
        }

        String resultDisplay;
        if (result.equals("passed")) {
            resultDisplay = "P";
            reviewListRecyclerView.viewResult.setBackground(ContextCompat.getDrawable(context, R.drawable.badge_passed) );
        } else if (result.equals("failed")) {
            resultDisplay = "F";
            reviewListRecyclerView.viewResult.setBackground(ContextCompat.getDrawable(context, R.drawable.badge_failed) );
        } else {
            resultDisplay = "CR";
            reviewListRecyclerView.viewResult.setBackground(ContextCompat.getDrawable(context, R.drawable.badge_cant_review) );
            reviewListRecyclerView.viewResult.setTextSize(10);
        }

        reviewListRecyclerView.viewResult.setText(resultDisplay);
        reviewListRecyclerView.viewReviewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewDetailsActivity = new Intent(context, ReviewsDetailActivity.class);
                viewDetailsActivity.putExtra("id", idString);
                viewDetailsActivity.putExtra("project_name", projectName);
                viewDetailsActivity.putExtra("assigned_at", assignedAtDetailDisplay);
                viewDetailsActivity.putExtra("completed_at", completedAtDetailDisplay);
                viewDetailsActivity.putExtra("user_name", userName);
                viewDetailsActivity.putExtra("result", result);
                viewDetailsActivity.putExtra("archive_url", archiveUrl);
                viewDetailsActivity.putExtra("filename", fileName);
                viewDetailsActivity.putExtra("elapsed_time", elapsedTime);
                viewDetailsActivity.putExtra("notes", studentNotes);
                viewDetailsActivity.putExtra("rating", rating);

                context.startActivity(viewDetailsActivity);
            }
        });


    }

    /* The inner RealmBaseAdapter
     * view count is applied here.
     *
     * getRealmAdapter is defined in RealmRecyclerViewAdapter.
     */
    @Override
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }
}
