package rocks.athrow.android_udacity_reviews;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;
import rocks.athrow.android_udacity_reviews.Data.FetchReviews;
import rocks.athrow.android_udacity_reviews.Data.Review;
import rocks.athrow.android_udacity_reviews.RealmAdapter.RealmReviewsAdapter;

/**
 * Created by josel on 7/5/2016.
 */
public class ReviewsListFragmentActivity extends Fragment {
    ReviewListAdapter reviewListAdapter;

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(reviewListAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set the adapter with the query results
        reviewListAdapter = new ReviewListAdapter();
        // Fetch all the reviews from the API and update the database
        // TODO: Move to update action (swipe down to refresh)
        FetchReviews fetchReviews = new FetchReviews(getContext(), reviewListAdapter);
        fetchReviews.execute();

        View rootView = inflater.inflate(R.layout.reviews_list, container, false);
        View recyclerView = rootView.findViewById(R.id.review_list);
        if ( recyclerView != null ) {
            setupRecyclerView((RecyclerView) recyclerView);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // ... calling super.onResume(), etc...
        // Perform the Realm database query
        // Get the existing review records
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        // Build the query looking at all users:
        RealmResults<Review> reviews = realm.where(Review.class).findAll().sort("id", Sort.DESCENDING);
        realm.commitTransaction();// Set the data and tell the RecyclerView to draw

        RealmReviewsAdapter realmAdapter = new RealmReviewsAdapter(getContext(), reviews);
        reviewListAdapter.setRealmAdapter(realmAdapter);
        reviewListAdapter.notifyDataSetChanged();
    }
}
