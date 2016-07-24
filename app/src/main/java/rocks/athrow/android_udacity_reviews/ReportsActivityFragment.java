package rocks.athrow.android_udacity_reviews;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportsActivityFragment extends Fragment {
    TextView date1;
    TextView date2;

    public ReportsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reports, container, false);
        date1 = (TextView) rootView.findViewById(R.id.reports_date1);
        date2 = (TextView) rootView.findViewById(R.id.reports_date2);

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
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
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
            String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear) + "-" + String.valueOf(dayOfMonth);
            date1.setText(date);
        }
    };
    DatePickerDialog.OnDateSetListener date2Set = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear) + "-" + String.valueOf(dayOfMonth);
            date2.setText(date);
        }
    };

}
