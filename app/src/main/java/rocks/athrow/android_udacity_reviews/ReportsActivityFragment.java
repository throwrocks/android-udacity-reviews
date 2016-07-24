package rocks.athrow.android_udacity_reviews;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportsActivityFragment extends Fragment {
    TextView date1;
    TextView date2;

    public ReportsActivityFragment() {
    }
    private final static String DATE_DISPLAY = "MM/dd/yy";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String PREF_REPORT_DATE1 = getContext().getResources().getString(R.string.report_date1);
        String PREF_REPORT_DATE2 = getContext().getResources().getString(R.string.report_date2);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String reportDate1 = sharedPref.getString(PREF_REPORT_DATE1, "null");
        String reportDate2 = sharedPref.getString(PREF_REPORT_DATE2, "null");

        View rootView = inflater.inflate(R.layout.fragment_reports, container, false);
        date1 = (TextView) rootView.findViewById(R.id.reports_date1);
        date2 = (TextView) rootView.findViewById(R.id.reports_date2);

        if ( !reportDate1.equals("null") && !reportDate2.equals("null")){
            date1.setText(reportDate1);
            date2.setText(reportDate2);
        }

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(1);
            }
        });
        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(2);
            }
        });
        return rootView;
    }

    private void showDatePicker(int dateNumber) {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Call back to capture selected date
         */
        if (dateNumber == 1) {
            date.setCallBack(date1Set);
        } else if (dateNumber == 2) {
            date.setCallBack(date2Set);
        }
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener date1Set = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Utilities util = new Utilities();
            Date selectedDate = new Date(year - 1900, monthOfYear, dayOfMonth);
            String date = util.getDateAsString(selectedDate, DATE_DISPLAY, null);
            date1.setText(date);
        }
    };
    DatePickerDialog.OnDateSetListener date2Set = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Utilities util = new Utilities();
            Date selectedDate = new Date(year - 1900, monthOfYear, dayOfMonth);
            String date = util.getDateAsString(selectedDate, DATE_DISPLAY, null);
            date2.setText(date);
        }
    };

}
