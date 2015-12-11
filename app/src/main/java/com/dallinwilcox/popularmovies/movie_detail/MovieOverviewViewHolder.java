package com.dallinwilcox.popularmovies.movie_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dallinwilcox.popularmovies.R;
import com.dallinwilcox.popularmovies.data.Movie;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dcwilcox on 12/4/2015.
 */
public class MovieOverviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.posterImageView) ImageView posterImage;
    @Bind(R.id.releaseDateText) TextView releaseDate;
    @Bind(R.id.overviewText) TextView overviewText;
    @Bind(R.id.voteAverageText) TextView rating;

    public MovieOverviewViewHolder(View overview) {
        super(overview);
        ButterKnife.bind(this, overview);

    }

    @Override
    public void onClick(View v) {

    }

    public void bind(int position, Movie movie) {
        Context context = posterImage.getContext();
        Glide.with(context)
                .load(movie.getPosterUrl())
                .centerCrop()
                .into(posterImage);
        //sometime when I get more time, investigate generating tint via palette from bitmap
        //see https://github.com/bumptech/glide/wiki/Custom-targets
        releaseDate.setText(movie.getFormattedReleaseDate());
        overviewText.setText(movie.getOverview());
        rating.setText(String.format(context.getString(R.string.rating), movie.getVote_average()));
    }
}
