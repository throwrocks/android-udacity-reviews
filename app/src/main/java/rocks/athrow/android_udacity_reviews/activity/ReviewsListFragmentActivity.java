package rocks.athrow.android_udacity_reviews.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;
import rocks.athrow.android_udacity_reviews.R;
import rocks.athrow.android_udacity_reviews.util.Utilities;
import rocks.athrow.android_udacity_reviews.data.FetchTask;
import rocks.athrow.android_udacity_reviews.data.RealmReview;
import rocks.athrow.android_udacity_reviews.realmadapter.RealmReviewsAdapter;

/**
 * ReviewsListFragmentActivity
 * Created by josel on 7/5/2016.
 */
public class ReviewsListFragmentActivity extends android.support.v4.app.Fragment implements MainActivity.ReviewsListFragmentCallback {
    ReviewListAdapter reviewListAdapter;
    private SwipeRefreshLayout swipeContainer;
    private final String MODULE_REVIEWS = "submissions_completed";
    private final String MODULE_FEEDBACKS = "student_feedbacks";
    FetchTask fetchReviews;
    FetchTask fetchFeedbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create a new adapter
        reviewListAdapter = new ReviewListAdapter(getContext());
        // Inflate the layout
        View rootView = inflater.inflate(R.layout.reviews_list, container, false);
        // Set up the RecyclerView
        View recyclerView = rootView.findViewById(R.id.review_list);
        if (recyclerView != null) {
            setupRecyclerView((RecyclerView) recyclerView);
        }

        // Set up the SwipeRefreshLayout
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // with a callBack to remove itself and present a toast when finishing the FetchReviews task
        final MainActivity.ReviewsListFragmentCallback callback = new MainActivity.ReviewsListFragmentCallback() {
            @Override
            public void onFetchReviewsCompleted() {
                swipeContainer.setRefreshing(false);
                Context context = getContext();
                CharSequence text = "Your reviews are up to date!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        };
        // and with a listener to trigger the FetchReviews task
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boolean isConnected = Utilities.isConnected(getContext());
                if (isConnected) {
                    fetchReviews = new FetchTask(getContext(), MODULE_REVIEWS, reviewListAdapter, callback);
                    fetchReviews.execute();
                    fetchFeedbacks = new FetchTask(getContext(), MODULE_FEEDBACKS, null, null);
                    fetchFeedbacks.execute();
                } else {
                    Context context = getContext();
                    CharSequence text = "You are not connected to a network";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    swipeContainer.setRefreshing(false);
                }
            }
        });
        // set up the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return rootView;
    }

    /**
     * setupRecyclerView
     *
     * @param recyclerView the RecyclerView for the Reviews to set up the adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(reviewListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<RealmReview> reviews = realm.where(RealmReview.class).findAll().sort("completed_at", Sort.DESCENDING);
        realm.commitTransaction();
        RealmReviewsAdapter realmAdapter = new RealmReviewsAdapter(getContext(), reviews);
        reviewListAdapter.setRealmAdapter(realmAdapter);
        reviewListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        swipeContainer.setRefreshing(false);
        if (fetchReviews != null) {
            fetchReviews.cancel(true);
        }
        if (fetchFeedbacks != null) {
            fetchFeedbacks.cancel(true);
        }

    }

    @Override
    public void onFetchReviewsCompleted() {

    }
}
