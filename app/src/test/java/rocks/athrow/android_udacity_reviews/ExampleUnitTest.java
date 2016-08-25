package rocks.athrow.android_udacity_reviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.mock.MockContext;
import android.util.Log;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.annotation.meta.When;

import rocks.athrow.android_udacity_reviews.data.API;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest extends Mockito {
    String APIKey;
    @Mock
    final Context context = mock(Context.class);
    @Mock
    final SharedPreferences sharedPrefs = mock(SharedPreferences.class);
    @Mock
    SharedPreferences.Editor editor = sharedPrefs.edit();

    @Test
    public void testAPI() throws Exception {
        String API_KEY = BuildConfig.UDACITY_REVIEWER_API_KEY;
        when(sharedPrefs.edit()).thenReturn(editor);
        editor.putString("api_key", API_KEY);
        editor.apply();
        APIKey = sharedPrefs.getString("api_key", "");
        assertTrue(!API_KEY.equals(""));
    }
    @Test
    public void testAPIGetReviews() throws Exception{
        String reviewsJSON = API.callAPI(APIKey, "reviews", null, null);
        assertTrue(reviewsJSON != null);
    }
    @Test
    public void testAPIGetFeedback() throws Exception{
        String feedbacksJSON = API.callAPI(APIKey, "feedbacks", null, null);
        assertTrue(feedbacksJSON != null);
    }

    @Test
    public void testAPIPassNullKey() throws Exception{
        String reviewsJSON = API.callAPI("sdasd", "feedbacks", null, null);
        Log.e("Results ", reviewsJSON);
        assertTrue(reviewsJSON != null);
    }
}