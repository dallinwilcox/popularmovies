package com.dallinwilcox.popularmovies.movie_detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.dallinwilcox.popularmovies.R;
import com.dallinwilcox.popularmovies.data.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dcwilcox on 10/22/2015.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    @Bind(R.id.detail_recycler) RecyclerView movieDetails;
    //referenced http://jakewharton.github.io/butterknife/
    private MovieDetailAdapter movieDetailAdapter;
    public static final String MOVIE_EXTRA = "MovieExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Movie movie = (Movie) getIntent().getExtras().getParcelable(MOVIE_EXTRA);
        movieDetailAdapter = new MovieDetailAdapter(this, movie);
        movieDetails.setLayoutManager(new LinearLayoutManager(this));
        movieDetails.setAdapter(movieDetailAdapter);


    }
}
