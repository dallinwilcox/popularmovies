package com.dallinwilcox.popularmovies.movie_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dcwilcox on 12/4/2015.
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ReviewViewHolder(View review) {
        super(review);
        review.setClickable(true);
        review.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
