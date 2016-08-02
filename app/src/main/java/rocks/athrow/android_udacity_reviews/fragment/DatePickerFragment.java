package rocks.athrow.android_udacity_reviews.fragment;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.facebook.stetho.common.Util;

import rocks.athrow.android_udacity_reviews.util.Utilities;

/**
 * Created by jose on 7/24/16.
 */
public class DatePickerFragment extends DialogFragment {

    private final static String FIELD_SELECTED_DATE = "selected_date";
    private final static String DATE_DISPLAY = "MM/dd/yy";
    private DatePickerDialog.OnDateSetListener ondateSet;

    public DatePickerFragment() {
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String selectedDate = bundle.getString(FIELD_SELECTED_DATE);
        Log.i("selectedDate", selectedDate);
        Date date = Utilities.getStringAsDate(selectedDate, DATE_DISPLAY, null);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(java.util.Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }

}