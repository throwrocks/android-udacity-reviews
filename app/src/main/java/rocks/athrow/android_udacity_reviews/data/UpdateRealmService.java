package rocks.athrow.android_udacity_reviews.data;

import android.app.IntentService;
import android.content.Intent;

/**
 * updateRealmService
 * A Service Class to handle updating the Realm database in the background
 * Created by josel on 9/1/2016.
 */
public class UpdateRealmService extends IntentService {
    private static final String TAG = UpdateRealmService.class.getSimpleName();
    public static final String ACTION_UPDATE = TAG + ".UPDATE";
    public static final String EXTRA_VALUES = TAG + ".ContentValues";


    public UpdateRealmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
