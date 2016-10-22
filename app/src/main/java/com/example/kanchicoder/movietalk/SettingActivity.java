package com.example.kanchicoder.movietalk;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by kanchicoder on 10/22/2016.
 */

public class SettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);      //  bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sort_key)));

    }
}
