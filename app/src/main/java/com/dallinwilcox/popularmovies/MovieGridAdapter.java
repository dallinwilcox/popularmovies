package com.dallinwilcox.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by dcwilcox on 10/9/2015.
 * <p/>
 * //referenced <a href="http://www.vogella.com/tutorials/AndroidRecyclerView/article.html#recyclerview_overview">
 * http://www.vogella.com/tutorials/AndroidRecyclerView/article.html#recyclerview_overview</a>
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridViewHolder> {
    private ArrayList<Movie> movieList;

    @Override
    public MovieGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieGridViewHolder holder, int position) {
        String posterURL = movieList.get(position).getQualifiedPosterUrl();
        Glide.with(holder.posterImageView.getContext()).load(posterURL).into(holder.posterImageView);
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieGridViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterImageView;

        public MovieGridViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.poster_image_view);
        }
    }
}
