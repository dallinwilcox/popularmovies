package com.dallinwilcox.popularmovies.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.dallinwilcox.popularmovies.R;

import java.util.Map;

/**
 * Created by dcwilcox on 10/26/2015.
 */
public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String PREF_SORT_BY_KEY = "pref_sort_by_key";
    public static final String PREF_SORT_ORDER_KEY = "pref_sort_order_key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        //referenced http://stackoverflow.com/a/2888607
        updateSummary(key);
    }

    private void updateSummary(String key) {
        Preference sortingPref = findPreference(key);
        if (sortingPref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) sortingPref;
            sortingPref.setSummary(listPref.getEntry());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //register listener to handle updates
        SharedPreferences sharedPrefs = getPreferenceScreen().getSharedPreferences();
                sharedPrefs.registerOnSharedPreferenceChangeListener(this);

        //display summaries for each preference, adding %s string placeholder to summary in xml
        //seems to not work until the preference is set at least once in the UI. Maybe it's a bug?
        for ( Map.Entry<String, ?> entry : sharedPrefs.getAll().entrySet())
        {
            updateSummary(entry.getKey());
        }
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
