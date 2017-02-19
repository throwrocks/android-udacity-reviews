package rocks.athrow.android_udacity_reviews.activity;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rocks.athrow.android_udacity_reviews.R;
import rocks.athrow.android_udacity_reviews.data.PreferencesHelper;


@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity{
    private static final String API_KEY = "api_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getPreferenceScreen().findPreference(API_KEY).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                updatePreference(API_KEY);
                return false;
            }
        });
        updatePreference(API_KEY);
    }

    private void updatePreference(String key){
        if (key.equals(API_KEY)){
            Preference preference = findPreference(key);
            if (preference instanceof EditTextPreference){
                EditTextPreference editTextPreference =  (EditTextPreference)preference;
                PreferencesHelper preferencesHelper = new PreferencesHelper(getApplicationContext());
                String apiKey = editTextPreference.getText();
                preferencesHelper.save(API_KEY, apiKey);
                if (editTextPreference.getText() != null ){
                    editTextPreference.setSummary(apiKey);
                }else{
                    editTextPreference.setSummary("");
                }
            }
        }
    }

    // TODO: Implement an option to delete the database
    private void deleteDatabase(){RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }
}