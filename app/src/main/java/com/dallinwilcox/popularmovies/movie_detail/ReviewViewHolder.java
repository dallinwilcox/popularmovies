package com.dallinwilcox.popularmovies.movie_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.dallinwilcox.popularmovies.R;
import com.dallinwilcox.popularmovies.data.Review;

/**
 * Created by dcwilcox on 12/4/2015.
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.review_author_text_view) TextView authorTextView;
    @Bind(R.id.review_content_text_view) TextView reviewText;

    public ReviewViewHolder(View review) {
        super(review);
        ButterKnife.bind(this, review);
        review.setClickable(true);
        review.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void bind(int position, Review review) {
        authorTextView.setText(review.getAuthor());
        reviewText.setText(review.getContent());
    }
}
