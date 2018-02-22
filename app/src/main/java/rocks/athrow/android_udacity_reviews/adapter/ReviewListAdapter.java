package rocks.athrow.android_udacity_reviews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Date;

import rocks.athrow.android_udacity_reviews.R;
import rocks.athrow.android_udacity_reviews.activity.ReviewsDetailActivity;
import rocks.athrow.android_udacity_reviews.util.Utilities;
import rocks.athrow.android_udacity_reviews.data.RealmReview;
import rocks.athrow.android_udacity_reviews.realmadapter.RealmRecyclerViewAdapter;

/**
 * ReviewListAdapter
 * Binds the data from the Review Realm Objects to the
 * ReviewListFragmentActivity RecyclerView
 */
public class ReviewListAdapter extends RealmRecyclerViewAdapter<RealmReview> {

    private final static String DATE_TIME_DISPLAY = "MM/dd/yy h:mm a";
    private final static String DATE_DISPLAY = "MM/dd/yy";
    private Context mContext;

    private class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout viewReviewItem;
        TextView viewReviewId;
        TextView viewProjectName;
        TextView viewCompletedAt;
        TextView viewUserName;
        TextView viewResult;
        TextView viewReviewNone;
        RatingBar ratingBar;

        ViewHolder(View view) {
            super(view);
            viewReviewItem = view.findViewById(R.id.review_item);
            viewProjectName = view.findViewById(R.id.review_item_project_name);
            viewCompletedAt = view.findViewById(R.id.review_item_completed_at);
            viewUserName = view.findViewById(R.id.review_user_name);
            viewUserName = view.findViewById(R.id.review_user_name);
            viewResult = view.findViewById(R.id.review_result);
            viewReviewId = view.findViewById(R.id.review_id);
            ratingBar = view.findViewById(R.id.rating_bar);
            viewReviewNone = view.findViewById(R.id.review_rating_none);
        }
    }

    public ReviewListAdapter(Context context) {
        this.mContext = context;
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
        final double price = reviewRecord.getPrice();
        final Date assignedAt = reviewRecord.getAssigned_at();
        final Date completedAt = reviewRecord.getCompleted_at();
        final String userName = reviewRecord.getUser_name();
        final String result = reviewRecord.getResult();
        final String archiveUrl = reviewRecord.getArchive_url();
        final String studentNotes = reviewRecord.getNotes();
        //------------------------------------------------------------------------------------------
        // Format the dates for the List and Detail Views
        //------------------------------------------------------------------------------------------
        final String completedAtListDisplay = Utilities.getDateAsString(completedAt, DATE_DISPLAY, null);
        final String completedAtDetailDisplay = Utilities.getDateAsString(completedAt, DATE_TIME_DISPLAY, null);
        final String assignedAtDetailDisplay = Utilities.getDateAsString(assignedAt, DATE_TIME_DISPLAY, null);
        //------------------------------------------------------------------------------------------
        // Get the elapsed time between start/end
        //------------------------------------------------------------------------------------------
        final String elapsedTime = reviewRecord.getElapsedTimeDisplay();
        //------------------------------------------------------------------------------------------
        // Get the filename from the archive url
        //------------------------------------------------------------------------------------------
        String[] urlItems = Utilities.stringSplit(archiveUrl, "/");
        int urtlItemsCount = urlItems.length;
        final String fileName = urlItems[urtlItemsCount - 1];
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

        if (rating == 0) {
            reviewListRecyclerView.viewReviewNone.setText("Not Rated");
            reviewListRecyclerView.viewReviewNone.setVisibility(View.VISIBLE);
            reviewListRecyclerView.ratingBar.setVisibility(View.GONE);
        } else if (rating > 0) {
            reviewListRecyclerView.viewReviewNone.setVisibility(View.GONE);
            reviewListRecyclerView.ratingBar.setVisibility(View.VISIBLE);
            reviewListRecyclerView.ratingBar.setRating(rating);
        }

        final Context context = viewHolder.itemView.getContext();

        String resultDisplay;
        switch (result){
            case "passed":
                resultDisplay = "P";
                reviewListRecyclerView.viewResult.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.badge_passed));
                break;
            case "failed":
                resultDisplay = "F";
                reviewListRecyclerView.viewResult.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.badge_failed));
                break;
            default:
                resultDisplay = "CR";
                reviewListRecyclerView.viewResult.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.badge_cant_review));
                reviewListRecyclerView.viewResult.setTextSize(10);
                break;
        }

        reviewListRecyclerView.viewResult.setText(resultDisplay);
        reviewListRecyclerView.viewReviewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewDetailsActivity = new Intent(context, ReviewsDetailActivity.class);
                viewDetailsActivity.putExtra("id", idString);
                viewDetailsActivity.putExtra("project_name", projectName);
                viewDetailsActivity.putExtra("price", price);
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
     * view reports_count is applied here.
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
