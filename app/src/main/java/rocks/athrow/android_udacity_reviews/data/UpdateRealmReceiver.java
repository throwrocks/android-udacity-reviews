package rocks.athrow.android_udacity_reviews.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import rocks.athrow.android_udacity_reviews.R;

/**
 * Created by josel on 9/1/2016.
 */
public class UpdateRealmReceiver {

    public BroadcastReceiver updateRealmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean result = intent.getExtras().getBoolean("result");

        }
    };

}
