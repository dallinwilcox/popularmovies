package com.dallinwilcox.popularmovies;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MovieGridActivity extends AppCompatActivity implements OnItemClick {
    private RecyclerView movieGrid;
    private MovieGridAdapter movieGridAdapter;
    private RecyclerView.LayoutManager movieGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        setContentView(R.layout.activity_all_movies);
        movieGrid = (RecyclerView) findViewById(R.id.movieGrid);
        movieGridLayoutManager = new GridLayoutManager(this, 2); //2 columns (spans)
        movieGrid.setLayoutManager(movieGridLayoutManager);

        movieGridAdapter = new MovieGridAdapter(getApplicationContext());
        movieGrid.setAdapter(movieGridAdapter);
        movieGridAdapter.setItemClick(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent (getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getApplicationContext() ,MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_EXTRA, movieGridAdapter.get(position));
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .registerOnSharedPreferenceChangeListener(movieGridAdapter);
    }
    @Override
    protected void onPause() {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .unregisterOnSharedPreferenceChangeListener(movieGridAdapter);
        super.onPause();
    }
}