package rocks.athrow.android_udacity_reviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


import rocks.athrow.android_udacity_reviews.data.API;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class UnitTests extends Mockito {
    private final String REVIEWS = "reviews";
    private final String FEEDBACKS = "feedbacks";
    @SuppressLint(EMPTY_STRING)
    private final String EMPTY_STRING = "";
    @SuppressLint(API_KEY)
    private final String API_KEY = "api_key";
    @SuppressLint(API_ERROR)
    private final String API_ERROR = "error: empty module argument";
    String mAPIKey;
    @Mock
    final Context context = mock(Context.class);
    @Mock
    final SharedPreferences sharedPrefs = mock(SharedPreferences.class);
    @Mock
    SharedPreferences.Editor editor = sharedPrefs.edit();


    @Test
    public void testAPIKey() throws Exception {
        String prefAPIKey = BuildConfig.UDACITY_REVIEWER_API_KEY;
        when(sharedPrefs.edit()).thenReturn(editor);
        editor.putString(API_KEY, prefAPIKey);
        editor.apply();
        mAPIKey = sharedPrefs.getString(API_KEY, EMPTY_STRING);
        assertTrue(!API_KEY.equals(EMPTY_STRING));
    }
    @Test
    public void testAPIGetReviews() throws Exception{
        String reviewsJSON = API.callAPI(mAPIKey, REVIEWS, null, null);
        assertTrue(reviewsJSON != null);
    }
    @Test
    public void testAPIGetFeedback() throws Exception{
        String feedbacksJSON = API.callAPI(mAPIKey, FEEDBACKS, null, null);
        assertTrue(feedbacksJSON != null);
    }

    @Test
    public void testAPIPassNullKey() throws Exception{
        String reviewsJSON = API.callAPI(null, REVIEWS, null, null);
        String feedbacksJSON = API.callAPI(null, FEEDBACKS, null, null);
        assertTrue(reviewsJSON.equals(API_ERROR));
        assertTrue(feedbacksJSON.equals(API_ERROR));
    }
}