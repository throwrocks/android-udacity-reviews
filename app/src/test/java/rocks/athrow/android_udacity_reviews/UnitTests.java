package rocks.athrow.android_udacity_reviews;

import android.content.ContentValues;

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
    String EMPTY_STRING = "";
    String API_KEY = "api_key";
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
            mJSONReviews = API.callFeedbacksAPI(mAPIKey, null, null);
        }
        if ( mJSONFeedbacks == null ){
            mJSONFeedbacks = API.callFeedbacksAPI(mAPIKey, null, null);
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
    public void testReviewsAPIPassNullKey() throws Exception {
        String results = API.callReviewsAPI(null, null, null);
        assertTrue(results == null);
    }

    @Test
    public void testFeedbacksAPIPassNullKey() throws Exception {
        String results = API.callFeedbacksAPI(null, null, null);
        assertTrue(results == null);
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