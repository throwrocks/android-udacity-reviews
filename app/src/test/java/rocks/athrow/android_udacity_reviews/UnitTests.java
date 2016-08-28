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
import rocks.athrow.android_udacity_reviews.data.JSONParser;
import rocks.athrow.android_udacity_reviews.data.PreferencesHelper;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class UnitTests extends Robolectric {
    String MODULE_REVIEWS = "submissions_completed";
    String MODULE_FEEDBACKS = "student_feedbacks";
    String EMPTY_STRING = "";
    String API_KEY = "api_key";
    String API_ERROR_INVALID_MODULE = "error: invalid module argument";
    String mAPIKey;
    PreferencesHelper mSharedPreferences;
    String mBuildAPIKey = BuildConfig.UDACITY_REVIEWER_API_KEY;
    String mJSONReviews = null;
    String mJSONFeedbacks = null;

    @Before
    public void setUp() throws Exception {
        if (mAPIKey == null) {
            mSharedPreferences = new PreferencesHelper(RuntimeEnvironment.application.getApplicationContext());
            mSharedPreferences.save(API_KEY, mBuildAPIKey);
            mAPIKey = mSharedPreferences.loadString(API_KEY, EMPTY_STRING);
        }
        if ( mJSONReviews == null ){
            ContentValues contentValues = new ContentValues();
            contentValues.put("module", MODULE_REVIEWS);
            contentValues.put("date_start", EMPTY_STRING);
            contentValues.put("date_end", EMPTY_STRING);
            mJSONReviews = API.callAPI(mAPIKey, contentValues);
        }
        if ( mJSONFeedbacks == null ){
            ContentValues contentValues = new ContentValues();
            contentValues.put("module", MODULE_FEEDBACKS);
            contentValues.put("date_start", EMPTY_STRING);
            contentValues.put("date_end", EMPTY_STRING);
            mJSONFeedbacks = API.callAPI(mAPIKey, contentValues);
        }
    }

    @Test
    public void testAPIGetReviews() throws Exception {
        assertTrue(mJSONReviews != null);
    }

    @Test
    public void testAPIGetFeedback() throws Exception {
        assertTrue(mJSONFeedbacks != null);
    }

    @Test
    public void testAPIPassNullKey() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("module", MODULE_REVIEWS);
        String results = API.callAPI(null, contentValues);
        assertTrue(results == null);
    }

    @Test
    public void testAPIPassInvalidModule() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put("module", "");
        String results = API.callAPI(API_KEY, contentValues);
        assertTrue(results.equals(API_ERROR_INVALID_MODULE));
    }

    @Test
    public void testParsingReviews() throws Exception {
        ContentValues[] parsedResults = JSONParser.parseReviews(mJSONReviews);
        assertTrue(parsedResults != null);
    }

    @Test
    public void testParsingFeedbacks() throws Exception {
        ContentValues[] parsedResults = JSONParser.parseFeedbacks(mJSONFeedbacks);
        assertTrue(parsedResults != null);
    }
}