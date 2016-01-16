package com.dallinwilcox.popularmovies;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.dallinwilcox.popularmovies.inf.OnItemClick;
import com.dallinwilcox.popularmovies.movie_detail.MovieDetailFragment;
import com.dallinwilcox.popularmovies.movie_detail.MovieDetailsActivity;
import com.dallinwilcox.popularmovies.settings.SettingsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieGridActivity extends AppCompatActivity implements OnItemClick {

    @Bind(R.id.movie_grid) RecyclerView movieGrid;
    private MovieGridAdapter movieGridAdapter;
    private boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);
        ButterKnife.bind(this);
        movieGridAdapter = new MovieGridAdapter(this);
        movieGrid.setAdapter(movieGridAdapter);
        movieGridAdapter.setItemClick(this);

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            isDualPane = true;
        }
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
            startActivity( new Intent (getApplicationContext(), SettingsActivity.class));
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
            .registerOnSharedPreferenceChangeListener(movieGridAdapter);
            //registering listener here intentionally, refer to
            // http://stackoverflow.com/a/8668012/2169923
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(int position) {

        if (isDualPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(MovieDetailFragment.MOVIE_EXTRA, movieGridAdapter.get(position));
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        }
        else {
        Intent intent = new Intent(getApplicationContext() ,MovieDetailsActivity.class);
        intent.putExtra(MovieDetailFragment.MOVIE_EXTRA, movieGridAdapter.get(position));
        startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .unregisterOnSharedPreferenceChangeListener(movieGridAdapter);
        super.onDestroy();
    }
}