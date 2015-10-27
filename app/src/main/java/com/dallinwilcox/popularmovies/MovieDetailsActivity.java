package com.dallinwilcox.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dcwilcox on 10/22/2015.
 */
public class MovieDetailsActivity extends AppCompatActivity {
    @Bind(R.id.titleText) TextView titleText;
    @Bind(R.id.posterImageView) ImageView posterImage;
    @Bind(R.id.releaseDateText) TextView releaseDate;
    @Bind(R.id.overviewText) TextView overviewText;
    @Bind(R.id.voteAverageText) TextView rating;
    //referenced http://jakewharton.github.io/butterknife/

    public static final String MOVIE_EXTRA = "MovieExtra";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Movie movie = (Movie) getIntent().getExtras().getParcelable(MOVIE_EXTRA);

        titleText.setText(movie.getOriginal_title());
        Glide.with(this)
                .load(movie.getPosterUrl())
                .centerCrop()
                .into(posterImage);
        //sometime when I get more time, investigate generating tint via palette from bitmap
        //see https://github.com/bumptech/glide/wiki/Custom-targets
        releaseDate.setText(movie.getFormattedReleaseDate());
        overviewText.setText(movie.getOverview());
        rating.setText(String.format(getString(R.string.rating), movie.getVote_average()));

    }
}
