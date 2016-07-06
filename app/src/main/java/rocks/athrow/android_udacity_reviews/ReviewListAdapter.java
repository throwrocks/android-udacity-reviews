package rocks.athrow.android_udacity_reviews;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import rocks.athrow.android_udacity_reviews.Data.Review;
import rocks.athrow.android_udacity_reviews.RealmAdapter.RealmRecyclerViewAdapter;

/**
 * ReviewListAdapter
 * Binds the data from the Review Realm Objects to the
 * ReviewListFragmentActivity RecyclerView
 */
public class ReviewListAdapter extends RealmRecyclerViewAdapter<Review> {
    private Context context;

    private class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout viewReviewItem;
        public TextView viewReviewId;
        public TextView viewProjectName;
        public TextView viewCompletedAt;
        public TextView viewUserName;
        public TextView viewResult;

        public ViewHolder(View view) {
            super(view);
            viewReviewItem = (LinearLayout) view.findViewById(R.id.review_item);
            viewProjectName = (TextView) view.findViewById(R.id.review_item_project_name);
            viewCompletedAt = (TextView) view.findViewById(R.id.review_item_completed_at);
            viewUserName = (TextView) view.findViewById(R.id.review_user_name);
            viewUserName = (TextView) view.findViewById(R.id.review_user_name);
            viewResult = (TextView) view.findViewById(R.id.review_result);
            viewReviewId = (TextView) view.findViewById(R.id.review_id);
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
        Review reviewRecord = getItem(position);
        // Set the review variables
        String id = Integer.toString(reviewRecord.getId());
        final String projectName = reviewRecord.getProject_name();
        String completedAt = reviewRecord.getCompleted_at();
        final String userName = reviewRecord.getUser_name();
        String result = reviewRecord.getResult();
        // Format the completed date to MM/dd/YY
        Utilities util = new Utilities();
        final String completedAtDisplay = util.formatDate(completedAt);
        // Set the views
        reviewListRecyclerView.viewReviewId.setText(id);
        reviewListRecyclerView.viewProjectName.setText(projectName);
        reviewListRecyclerView.viewCompletedAt.setText(completedAtDisplay);
        reviewListRecyclerView.viewUserName.setText(userName);
        reviewListRecyclerView.viewUserName.setText(userName);

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
                viewDetailsActivity.putExtra("project_name", projectName);
                viewDetailsActivity.putExtra("completed_at", completedAtDisplay);
                viewDetailsActivity.putExtra("user_name", userName);
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
