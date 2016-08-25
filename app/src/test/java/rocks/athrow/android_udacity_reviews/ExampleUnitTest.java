package rocks.athrow.android_udacity_reviews;

import org.junit.Test;

import rocks.athrow.android_udacity_reviews.data.API;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void testAPI() throws Exception {
        String reviewsJSON = API.callAPI("reviews", null, null);
        String feedbacksJSON = API.callAPI("feedbacks", null, null);
        assertTrue(reviewsJSON != null);
        assertTrue(feedbacksJSON != null);


    }
}