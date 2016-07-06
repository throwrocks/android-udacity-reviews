package rocks.athrow.android_udacity_reviews;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;
import rocks.athrow.android_udacity_reviews.Data.FetchTask;
import rocks.athrow.android_udacity_reviews.Data.Review;
import rocks.athrow.android_udacity_reviews.RealmAdapter.RealmReviewsAdapter;

/**
 * Created by josel on 7/5/2016.
 */
public class ReviewsListFragmentActivity extends Fragment implements ReviewsListActivity.ReviewsListFragmentCallback {
    ReviewListAdapter reviewListAdapter;
    private SwipeRefreshLayout swipeContainer;
    private final String MODULE_REVIEWS = "submissions_completed";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create a new adapter
        reviewListAdapter = new ReviewListAdapter(getContext());
        // Inflate the layout
        View rootView = inflater.inflate(R.layout.reviews_list, container, false);
        // Set up the RecyclerView
        View recyclerView = rootView.findViewById(R.id.review_list);
        if ( recyclerView != null ) {
            setupRecyclerView((RecyclerView) recyclerView);
        }
        // Set up the SwipeRefreshLayout
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // with a callBack to remove itself and present a toast when finishing the FetchReviews task
        final ReviewsListActivity.ReviewsListFragmentCallback callback = new ReviewsListActivity.ReviewsListFragmentCallback() {
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
                FetchTask fetchReviews = new FetchTask(getContext(), MODULE_REVIEWS, reviewListAdapter,callback );
                fetchReviews.execute();
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
        RealmResults<Review> reviews = realm.where(Review.class).findAll().sort("id", Sort.DESCENDING);
        realm.commitTransaction();
        RealmReviewsAdapter realmAdapter = new RealmReviewsAdapter(getContext(), reviews);
        reviewListAdapter.setRealmAdapter(realmAdapter);
        reviewListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFetchReviewsCompleted() {

    }


}
