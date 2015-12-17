package com.dallinwilcox.popularmovies.movie_detail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dallinwilcox.popularmovies.MovieGridActivity;

import com.dallinwilcox.popularmovies.R;
import com.dallinwilcox.popularmovies.data.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieGridActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailsActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    //referenced http://jakewharton.github.io/butterknife/

    public static final String MOVIE_EXTRA = "MovieExtra";
    private Movie movie;
    private MovieDetailAdapter movieDetailAdapter;
    @Bind(R.id.detail_recycler)
    RecyclerView movieDetails;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    /**
     * Required empty constructor
     */
    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Activity activity = getActivity();
        if (getArguments().containsKey(MOVIE_EXTRA)) {
            movie = (Movie) getArguments().getParcelable(MovieDetailFragment.MOVIE_EXTRA);
        }
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(movie.getTitle());
        }

        ButterKnife.bind(this, view);
        movieDetailAdapter = new MovieDetailAdapter(activity, movie);
        movieDetails.setLayoutManager(new LinearLayoutManager(activity));
        movieDetails.setAdapter(movieDetailAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
