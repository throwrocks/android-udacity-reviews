package rocks.athrow.android_udacity_reviews.data;

/**
 * APIResponse
 * Created by josel on 9/25/2016.
 */

final class APIResponse {

    private String responseText;
    private int responseCode;

    APIResponse() {
    }

    /**
     * setResponseCode
     *
     * @param responseCode the API's response code number
     */
    void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * setResponseText
     *
     * @param responseText the API's response text
     */
    void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    /**
     * getResponseCode
     *
     * @return the API's response code number
     */
    int getResponseCode() {
        return responseCode;
    }

    /**
     * getResponseText
     *
     * @return the API's response text
     */
    String getResponseText() {
        return responseText;
    }

}
