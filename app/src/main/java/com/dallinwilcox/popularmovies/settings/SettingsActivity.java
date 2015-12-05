package com.dallinwilcox.popularmovies.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dcwilcox on 10/26/2015.
 */
public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}

