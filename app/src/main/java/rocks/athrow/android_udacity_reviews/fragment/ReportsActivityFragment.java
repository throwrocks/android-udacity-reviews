package rocks.athrow.android_udacity_reviews.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import rocks.athrow.android_udacity_reviews.R;
import rocks.athrow.android_udacity_reviews.adapter.ProjectSummaryAdapter;
import rocks.athrow.android_udacity_reviews.data.PreferencesHelper;
import rocks.athrow.android_udacity_reviews.data.ReportQueryTask;
import rocks.athrow.android_udacity_reviews.data.SummaryObject;
import rocks.athrow.android_udacity_reviews.interfaces.OnReportQueryCompleted;
import rocks.athrow.android_udacity_reviews.util.Constants;
import rocks.athrow.android_udacity_reviews.util.Utilities;

/**
 * ReportsActivityFragment
 */
public class ReportsActivityFragment extends Fragment {

    private final static String DATE_DISPLAY = "MM/dd/yy";
    private final static String FIELD_SELECTED_DATE = "selected_date";
    private View mRootView;
    private TextView mDate1View;
    private TextView mDate2View;
    private View mRecyclerView;
    private LinearLayout mReportBodyView;
    private LinearLayout mReportFooterView;
    private TextView mReportMessage;
    private ArrayList<SummaryObject> mSummaryObjects;
    private ProjectSummaryAdapter mAdapter;

    public ReportsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get the dates from the shared preferences
        PreferencesHelper sharedPref = new PreferencesHelper(getActivity());
        String reportDate1 = sharedPref.loadString(Constants.PREF_REPORT_DATE1, Constants.PREF_EMPTY_STRING);
        String reportDate2 = sharedPref.loadString(Constants.PREF_REPORT_DATE2, Constants.PREF_EMPTY_STRING);
        // Inflate the layout
        mRootView = inflater.inflate(R.layout.fragment_reports, container, false);
        mRecyclerView = mRootView.findViewById(R.id.reports_project_summary);
        mReportBodyView = mRootView.findViewById(R.id.report_body);
        mReportFooterView = mRootView.findViewById(R.id.report_footer);
        mReportMessage = mRootView.findViewById(R.id.reports_message);
        // Set the date range views
        mDate1View = mRootView.findViewById(R.id.reports_date1);
        mDate2View = mRootView.findViewById(R.id.reports_date2);
        Button queryButton = mRootView.findViewById(R.id.reports_query_button);
        // Set the views
        if (!reportDate1.equals(Constants.PREF_EMPTY_STRING) && !reportDate2.equals(Constants.PREF_EMPTY_STRING)) {
            mDate1View.setText(reportDate1);
            mDate2View.setText(reportDate2);
        }
        mDate1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedDate = mDate1View.getText().toString();
                showDatePicker(1, selectedDate);
            }
        });
        mDate2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedDate = mDate2View.getText().toString();
                showDatePicker(2, selectedDate);
            }
        });
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportQuery();
            }
        });
        reportQuery();
        return mRootView;
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
                if (summaryObjects != null) {
                    setReportViews(summaryObjects);
                } else {
                    Context context = getContext();
                    CharSequence text = context.getResources().getString(R.string.reports_no_reviews_found);
                    mReportMessage.setVisibility(View.VISIBLE);
                    mReportBodyView.setVisibility(View.GONE);
                    mReportFooterView.setVisibility(View.GONE);
                    mReportMessage.setText(text);
                }
            }
        };
        // Add query conditions
        String selectedDate1 = mDate1View.getText().toString();
        String selectedDate2 = mDate2View.getText().toString();
        Date date1 = Utilities.getStringAsDate(selectedDate1, DATE_DISPLAY, null);
        Date date2 = Utilities.getDateEnd(Utilities.getStringAsDate(selectedDate2, DATE_DISPLAY, null));
        // Validate the dates
        if (date1.before(date2) || date1.equals(date2)) {
            ReportQueryTask queryTask = new ReportQueryTask(getActivity(), onReportQueryCompleted, date1, date2);
            queryTask.execute();
        } else {
            mReportMessage.setVisibility(View.VISIBLE);
            mReportBodyView.setVisibility(View.GONE);
            mReportFooterView.setVisibility(View.GONE);
            Context context = getContext();
            CharSequence text = context.getResources().getString(R.string.reports_invalid_date_range);
            mReportMessage.setText(text);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ProjectSummaryAdapter(mSummaryObjects, getContext());
        recyclerView.setAdapter(mAdapter);
    }

    private void setReportViews(ArrayList<SummaryObject> summaryObject) {
        SummaryObject summaryReviews = summaryObject.get(0);
        String countDisplay = summaryReviews.getReviewsCount();
        String hoursDisplay = summaryReviews.getElapsedTime();
        String revenueDisplay = summaryReviews.getReviewsRevenue();
        TextView countView = mRootView.findViewById(R.id.reports_count_reviews);
        TextView hoursView = mRootView.findViewById(R.id.report_hours);
        TextView revenueView = mRootView.findViewById(R.id.reports_revenue);
        revenueView.setText(revenueDisplay);
        hoursView.setText(hoursDisplay);
        countView.setText(countDisplay);
        summaryObject.remove(0);
        mReportMessage.setVisibility(View.GONE);
        mReportBodyView.setVisibility(View.VISIBLE);
        mReportFooterView.setVisibility(View.VISIBLE);
        setupRecyclerView((RecyclerView) mRecyclerView);
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
            PreferencesHelper sharedPref = new PreferencesHelper(getActivity());
            sharedPref.save(Constants.PREF_REPORT_DATE1, date);
            mDate1View.setText(date);
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
            PreferencesHelper sharedPref = new PreferencesHelper(getActivity());
            sharedPref.save(Constants.PREF_REPORT_DATE2, date);
            mDate2View.setText(date);
        }
    };
}
