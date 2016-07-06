package rocks.athrow.android_udacity_reviews.RealmAdapter;

import android.content.Context;

import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.Data.Review;

/**
 * RealmReviewsAdapter
 * I'm going to need one more convenience class to help create a RealmModelAdapter
 * supporting the RealmObject type I want
 * http://gradlewhy.ghost.io/realm-results-with-recyclerview/
 */
public class RealmReviewsAdapter extends RealmModelAdapter<Review> {
    public RealmReviewsAdapter(Context context, RealmResults<Review> realmResults) {
        super(context, realmResults);
    }
}
