package com.dallinwilcox.popularmovies;

import android.app.Application;
import android.preference.PreferenceManager;
import android.util.Log;


/**
 * Created by dcwilcox on 10/27/2015.
 */
public class MovieApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //populate default preferences on first app creation
        //reference http://developer.android.com/guide/topics/ui/settings.html#Defaults
        //doing it here instead of in an activity so it doesn't matter which activity is launched
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        Log.d("MoviesApp", "onCreate");
        Log.d("MoviesApp", PreferenceManager.getDefaultSharedPreferences(this).getString("sort_preference_key", ""));
    }
}
