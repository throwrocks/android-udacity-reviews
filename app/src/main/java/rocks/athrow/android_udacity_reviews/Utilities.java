package rocks.athrow.android_udacity_reviews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by josel on 7/5/2016.
 */
public class Utilities {

    public String formatDate(String oldDateString){
        final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        final String NEW_FORMAT = "MM/dd/yy";

        String newDateString;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, Locale.getDefault());
        try{
            Date d = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
        }
        catch (ParseException e) {
            return e.toString();
        }

        return newDateString;
    }


}
