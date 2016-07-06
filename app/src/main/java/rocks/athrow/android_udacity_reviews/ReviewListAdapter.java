package rocks.athrow.android_udacity_reviews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        public TextView viewReviewId;
        public TextView viewProjectName;
        public TextView viewCompletedAt;
        public TextView viewUserName;
        public TextView viewResult;

        public ViewHolder(View view) {
            super(view);
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
        //Review event = getItem(i);
        Review reviewRecord = getItem(position);
        String id = Integer.toString(reviewRecord.getId());
        String projectName = reviewRecord.getProject_name();
        String completedAtRaw = reviewRecord.getCompleted_at();
        Utilities util = new Utilities();
        String completedAt = util.formatDate(completedAtRaw);
        String userName = reviewRecord.getUser_name();
        String result = reviewRecord.getResult();
        reviewListRecyclerView.viewReviewId.setText(id);
        reviewListRecyclerView.viewProjectName.setText(projectName);
        reviewListRecyclerView.viewCompletedAt.setText(completedAt);
        reviewListRecyclerView.viewUserName.setText(userName);
        reviewListRecyclerView.viewUserName.setText(userName);


        int resultBackgroundColor;
        if (result.equals("passed")) {
            reviewListRecyclerView.viewResult.setBackground(ContextCompat.getDrawable(context, R.drawable.badge_passed) );
        } else if (result.equals("failed")) {
            reviewListRecyclerView.viewResult.setBackground(ContextCompat.getDrawable(context, R.drawable.badge_failed) );
        } else {
            result = "can't review";
            reviewListRecyclerView.viewResult.setBackground(ContextCompat.getDrawable(context, R.drawable.badge_cant_review) );
            reviewListRecyclerView.viewResult.setTextSize(10);
        }



        reviewListRecyclerView.viewResult.setText(result);

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
