package rocks.athrow.android_udacity_reviews.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rocks.athrow.android_udacity_reviews.BuildConfig;
import rocks.athrow.android_udacity_reviews.R;
import rocks.athrow.android_udacity_reviews.adapter.TabNavigationAdapter;
import rocks.athrow.android_udacity_reviews.data.PreferencesHelper;
import rocks.athrow.android_udacity_reviews.util.Constants;
import rocks.athrow.android_udacity_reviews.util.Utilities;

public class MainActivity extends AppCompatActivity {

    private final static String DATE_DISPLAY = "MM/dd/yy";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set DEBUG to true to clear the database when the app starts / for testing
        boolean DEBUG = false;

        setContentView(R.layout.activity_main);
        PreferencesHelper sharedPref = new PreferencesHelper(this);
        String reportDate1 = sharedPref.loadString(Constants.PREF_REPORT_DATE1, Constants.PREF_EMPTY_STRING);
        String reportDate2 = sharedPref.loadString(Constants.PREF_REPORT_DATE2, Constants.PREF_EMPTY_STRING);
        if ( reportDate1.equals(Constants.PREF_EMPTY_STRING) && reportDate2.equals(Constants.PREF_EMPTY_STRING)){
            Date date1 = Utilities.getTodaysDate(DATE_DISPLAY);
            Date date2 = Utilities.getTodaysDate(DATE_DISPLAY);
            reportDate1 = Utilities.getDateAsString(date1, DATE_DISPLAY, null);
            reportDate2 = Utilities.getDateAsString(date2, DATE_DISPLAY, null);
            sharedPref.save(Constants.PREF_REPORT_DATE1, reportDate1);
            sharedPref.save(Constants.PREF_REPORT_DATE2, reportDate2);
        }
        // Save the API key to the shared preferences
        // TODO: When the settings are properly implemented this will not be necessary
        String API_KEY = BuildConfig.UDACITY_REVIEWER_API_KEY;;
        sharedPref.save(Constants.PREF_API_KEY, API_KEY);

        TabNavigationAdapter mSectionsPagerAdapter = new TabNavigationAdapter(getSupportFragmentManager(), this);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // Set up the TabLayout with the PageViewer
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabNavigation);
        mTabLayout.setupWithViewPager(mViewPager);

        // Stetho used to inspect the Realm database
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        if (DEBUG) {
            // Delete everything (for testing only)
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
            Realm.setDefaultConfiguration(realmConfig);
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // TODO: Implement two pane layout
        if (findViewById(R.id.review_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    /**
     * ReviewsListFragmentCallback
     * An interface to update the UI after getting new reviews from the API
     */
    public interface ReviewsListFragmentCallback {
        void onFetchReviewsCompleted(int result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings_main) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
