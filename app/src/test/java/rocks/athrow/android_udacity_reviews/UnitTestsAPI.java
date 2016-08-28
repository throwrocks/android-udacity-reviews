package rocks.athrow.android_udacity_reviews;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import rocks.athrow.android_udacity_reviews.data.API;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class UnitTestsAPI extends Robolectric {
    String REVIEWS = "submissions_completed";
    String FEEDBACKS = "student_feedbacks";
    String EMPTY_STRING = "";
    String API_KEY = "api_key";
    String API_ERROR = "error: empty module argument";
    String mAPIKey;
    SharedPreferences mMockSharedPreferences;
    String prefAPIKey = BuildConfig.UDACITY_REVIEWER_API_KEY;

    @Before
    public void testAPIKey() throws Exception {
        mMockSharedPreferences = RuntimeEnvironment.application.
                getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor mMockEditor = mMockSharedPreferences.edit();
        mMockEditor.putString(API_KEY, prefAPIKey);
        mMockEditor.apply();
        mAPIKey = mMockSharedPreferences.getString(API_KEY, EMPTY_STRING);
    }

    @Test
    public void testAPIGetReviews() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("module", REVIEWS);
        contentValues.put("date_start", EMPTY_STRING);
        contentValues.put("date_end", EMPTY_STRING);
        String reviewsJSON = API.callAPI(mAPIKey, contentValues);
        assertTrue(reviewsJSON != null);
    }

    @Test
    public void testAPIGetFeedback() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("module", FEEDBACKS);
        contentValues.put("date_start", EMPTY_STRING);
        contentValues.put("date_end", EMPTY_STRING);
        String result = API.callAPI(mAPIKey, contentValues);
        assertTrue(result != null);
    }

    @Test
    public void testAPIPassNullKey() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("module", FEEDBACKS);
        contentValues.put("date_start", EMPTY_STRING);
        contentValues.put("date_end", EMPTY_STRING);
        String reviewsJSON = API.callAPI(null, contentValues);
        contentValues.put("module", FEEDBACKS);
        String feedbacksJSON = API.callAPI(null, contentValues);
        assertTrue(reviewsJSON == null);
        assertTrue(feedbacksJSON == null);
    }
}