package rocks.athrow.android_udacity_reviews.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rocks.athrow.android_udacity_reviews.R;
import rocks.athrow.android_udacity_reviews.util.Utilities;
import rocks.athrow.android_udacity_reviews.data.RealmReview;;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportsActivityFragment extends Fragment {
    private final static String DATE_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    View rootView;
    TextView date1;
    TextView date2;
    Button queryButton;
    String PREF_REPORT_DATE1;
    String PREF_REPORT_DATE2;
    public ReportsActivityFragment() {
    }
    private final static String DATE_DISPLAY = "MM/dd/yy";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PREF_REPORT_DATE1 = getContext().getResources().getString(R.string.report_date1);
        PREF_REPORT_DATE2 = getContext().getResources().getString(R.string.report_date2);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String reportDate1 = sharedPref.getString(PREF_REPORT_DATE1, "null");
        String reportDate2 = sharedPref.getString(PREF_REPORT_DATE2, "null");

        rootView = inflater.inflate(R.layout.fragment_reports, container, false);
        date1 = (TextView) rootView.findViewById(R.id.reports_date1);
        date2 = (TextView) rootView.findViewById(R.id.reports_date2);
        queryButton = (Button) rootView.findViewById(R.id.reports_query_button);

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
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportQuery();
            }
        });
        return rootView;
    }

    /**
     * showDatePicker
     * @param dateNumber the date number (1 FROM or 2 TO)
     */
    private void showDatePicker(int dateNumber) {
        DatePickerFragment date = new DatePickerFragment();
        // Set Call back to capture selected date
        if (dateNumber == 1) {
            date.setCallBack(date1Set);
        } else if (dateNumber == 2) {
            date.setCallBack(date2Set);
        }
        date.show(getFragmentManager(), "Date Picker");
    }

    public void reportQuery(){
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmQuery<RealmReview> query = realm.where(RealmReview.class);
        // Add query conditions:
        String selectedDate1 = date1.getText().toString();
        String selectedDate2 = date2.getText().toString();
        Date date1 = Utilities.getStringAsDate(selectedDate1, DATE_DISPLAY, "UTC" );
        Date date2 = Utilities.getStringAsDate(selectedDate2, DATE_DISPLAY, "UTC" );
        Log.i("date1 ", "" + date1);
        Log.i("date2 ", "" + date2);
        query.between("completed_at", date1, date2);
        // Execute the query:
        RealmResults<RealmReview> results = query.findAll(); realm.commitTransaction();
        int count = results.size();
        String countDisplay = Integer.toString(count);
        Number revenue = results.sum("price");
        String revenueDisplay = Utilities.formatCurrency(revenue.doubleValue());
        realm.close();

        TextView revenueView = (TextView) rootView.findViewById(R.id.reports_revenue);
        TextView countView = (TextView) rootView.findViewById(R.id.reports_count_reviews);
        revenueView.setText(revenueDisplay);
        countView.setText(countDisplay);

    }

    /**
     * date1Set
     * Listener to set the date 1 (FROM)
     */
    DatePickerDialog.OnDateSetListener date1Set = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Date selectedDate = new Date(year - 1900, monthOfYear, dayOfMonth);
            String date = Utilities.getDateAsString(selectedDate, DATE_DISPLAY, null);
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(PREF_REPORT_DATE1, date);
            editor.apply();
            date1.setText(date);
        }
    };
    /**
     * date2Set
     * Listener to set the date2 (TO)
     */
    DatePickerDialog.OnDateSetListener date2Set = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Date selectedDate = new Date(year - 1900, monthOfYear, dayOfMonth);
            String date = Utilities.getDateAsString(selectedDate, DATE_DISPLAY, null);
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(PREF_REPORT_DATE2, date);
            editor.apply();
            date2.setText(date);
        }
    };

}
