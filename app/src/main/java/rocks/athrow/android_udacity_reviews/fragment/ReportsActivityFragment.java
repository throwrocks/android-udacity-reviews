package rocks.athrow.android_udacity_reviews.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import rocks.athrow.android_udacity_reviews.R;
import rocks.athrow.android_udacity_reviews.adapter.ProjectSummaryAdapter;
import rocks.athrow.android_udacity_reviews.data.ReportQueryTask;
import rocks.athrow.android_udacity_reviews.data.SummaryObject;
import rocks.athrow.android_udacity_reviews.interfaces.OnReportQueryCompleted;
import rocks.athrow.android_udacity_reviews.util.Constants;
import rocks.athrow.android_udacity_reviews.util.Utilities;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportsActivityFragment extends Fragment {

    private final static String DATE_DISPLAY = "MM/dd/yy";
    private final static String FIELD_SELECTED_DATE = "selected_date";
    private View rootView;
    private TextView date1;
    private TextView date2;
    private View recyclerView;
    private ArrayList<SummaryObject> mSummaryObjects;
    private ProjectSummaryAdapter mAdapter;

    public ReportsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get the dates from the shared preferences
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String reportDate1 = sharedPref.getString(Constants.PREF_REPORT_DATE1, "null");
        String reportDate2 = sharedPref.getString(Constants.PREF_REPORT_DATE2, "null");
        // Inflate the layout
        rootView = inflater.inflate(R.layout.fragment_reports, container, false);
        recyclerView = rootView.findViewById(R.id.reports_project_summary);
        // Set the date range views
        date1 = (TextView) rootView.findViewById(R.id.reports_date1);
        date2 = (TextView) rootView.findViewById(R.id.reports_date2);
        Button queryButton = (Button) rootView.findViewById(R.id.reports_query_button);
        // Set the views
        if (!reportDate1.equals("null") && !reportDate2.equals("null")) {
            date1.setText(reportDate1);
            date2.setText(reportDate2);
        }

        date1.setOnClickListener(new View.OnClickListener() {
            final String selectedDate = date1.getText().toString();

            @Override
            public void onClick(View view) {
                showDatePicker(1, selectedDate);
            }
        });
        date2.setOnClickListener(new View.OnClickListener() {
            final String selectedDate = date2.getText().toString();

            @Override
            public void onClick(View view) {
                showDatePicker(2, selectedDate);
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
     *
     * @param dateNumber the date number (1 FROM or 2 TO)
     */
    private void showDatePicker(int dateNumber, String selectedDate) {
        // Create a bundle to pass the selected date to the fragment
        Bundle bundle = new Bundle();
        bundle.putString(FIELD_SELECTED_DATE, selectedDate);
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(bundle);
        // Set call back to capture selected date
        if (dateNumber == 1) {
            datePickerFragment.setCallBack(date1Set);
        } else if (dateNumber == 2) {
            datePickerFragment.setCallBack(date2Set);
        }
        // Show the fragment
        datePickerFragment.show(getFragmentManager(), "Date Picker");
    }

    /**
     * reportQuery
     * Find the records by date range and display the results
     */
    private void reportQuery() {

        OnReportQueryCompleted onReportQueryCompleted = new OnReportQueryCompleted() {
            @Override
            public void OnReportQueryCompleted(ArrayList<SummaryObject> summaryObjects) {
                mSummaryObjects = summaryObjects;
                setReportViews(summaryObjects);

            }
        };
        // Add query conditions
        String selectedDate1 = date1.getText().toString();
        String selectedDate2 = date2.getText().toString();
        Date date1 = Utilities.getStringAsDate(selectedDate1, DATE_DISPLAY, null);
        Date date2 = Utilities.getDateEnd(Utilities.getStringAsDate(selectedDate2, DATE_DISPLAY, null));
        ReportQueryTask queryTask = new ReportQueryTask(getActivity(), onReportQueryCompleted, date1, date2);
        queryTask.execute();


    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ProjectSummaryAdapter(mSummaryObjects);
        recyclerView.setAdapter(mAdapter);
    }

    private void setReportViews(ArrayList<SummaryObject> summaryObject) {
        Log.e("summaryObject", "" + summaryObject.size());
        SummaryObject summaryReviews = summaryObject.get(0);
        String countDisplay = summaryReviews.getReviewsCount();
        String revenueDisplay = summaryReviews.getReviewsRevenue();
        TextView revenueView = (TextView) rootView.findViewById(R.id.reports_revenue);
        TextView countView = (TextView) rootView.findViewById(R.id.reports_count_reviews);
        revenueView.setText(revenueDisplay);
        countView.setText(countDisplay);
        summaryObject.remove(0);
        setupRecyclerView((RecyclerView) recyclerView);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * date1Set
     * Listener to set the date 1 (FROM)
     */
    private final DatePickerDialog.OnDateSetListener date1Set = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Date selectedDate = new Date(year - 1900, monthOfYear, dayOfMonth);
            String date = Utilities.getDateAsString(selectedDate, DATE_DISPLAY, null);
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Constants.PREF_REPORT_DATE1, date);
            editor.apply();
            date1.setText(date);
        }
    };
    /**
     * date2Set
     * Listener to set the date2 (TO)
     */
    private final DatePickerDialog.OnDateSetListener date2Set = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Date selectedDate = new Date(year - 1900, monthOfYear, dayOfMonth);
            String date = Utilities.getDateAsString(selectedDate, DATE_DISPLAY, null);
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Constants.PREF_REPORT_DATE2, date);
            editor.apply();
            date2.setText(date);
        }
    };
}
