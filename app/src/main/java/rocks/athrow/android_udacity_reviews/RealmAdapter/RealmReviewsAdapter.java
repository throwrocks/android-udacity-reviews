package rocks.athrow.android_udacity_reviews.RealmAdapter;

import android.content.Context;

import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.Data.RealmReview;

/**
 * RealmReviewsAdapter
 * I'm going to need one more convenience class to help create a RealmModelAdapter
 * supporting the RealmObject type I want
 * http://gradlewhy.ghost.io/realm-results-with-recyclerview/
 */
public class RealmReviewsAdapter extends RealmModelAdapter<RealmReview> {
    public RealmReviewsAdapter(Context context, RealmResults<RealmReview> realmResults) {
        super(context, realmResults);
    }
}
