package com.dallinwilcox.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dcwilcox on 10/22/2015.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "MovieExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movie = (Movie) getIntent().getExtras()
                .getParcelable(MOVIE_EXTRA);
    }
}
