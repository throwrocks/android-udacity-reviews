package rocks.athrow.android_udacity_reviews;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Utilities
 * General Utilities
 * Created by josel on 7/5/2016.
 */
public class Utilities {

    /**
     * formatDate
     * This method is used to convert the dat format
     *
     * @param oldDateString yyyy-MM-dd'T'HH:mm:ss.SSS'Z
     * @return newString MM/dd/yy
     */
    public String formatDate(String oldDateString, String format) {
        final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        final String NEW_FORMAT = format;

        String newDateString;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, Locale.getDefault());
        try {
            Date d = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
        } catch (ParseException e) {
            return e.toString();
        }

        return newDateString;
    }

    /**
     * elapsedTime
     *
     * @param dateStart
     * @param dateEnd
     * @return the elapsed hours and minutes
     */
    public String elapsedTime(String dateStart, String dateEnd) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateEnd);
            // Get msec from each, and subtract.
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            return Long.toString(diffHours) + ":" + Long.toString(diffMinutes);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * StringSplit
     */
    public String[] stringSplit(String string, String splitCharacter) {
        return string.split(splitCharacter);

    }

    /**
     * buildStringFromArray
     * @param stringArray the String[] to convert
     * @param separator the string separator
     * @return the separated string
     */
    public String buildStringFromArray(ArrayList<String> stringArray, String separator){
        if (stringArray.size() > 0) {
            StringBuilder nameBuilder = new StringBuilder();
            for (String n : stringArray) {
                    nameBuilder.append(n).append(separator);
                Log.i("loop", "loop");
            }
            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
            return nameBuilder.toString();
        } else {
            return null;
        }
    }

}
