package rocks.athrow.android_udacity_reviews.util;

/**
 * Created by Anthony M. Santiago on 8/1/2016.
 * 
 * Class to hold global constants.
 */
public final class Constants {

    private Constants(){ throw new AssertionError("No Constants instances for you!");}
    // Shared Preferences Constants
    public static final String PREF_API_KEY = "api_key";
    public static final String PREF_REPORT_DATE1 = "reports_date1";
    public static final String PREF_REPORT_DATE2 = "reports_date2";
    public static final String PREF_EMPTY_STRING = "";
    // API Constants
    public static final String API_MODULE = "module";
    public static final String API_MODULE_REVIEWS = "submissions_completed";
    public static final String API_MODULE_FEEDBACKS = "student_feedbacks";
    // Utility Constants
    public static final String UTIL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
}
