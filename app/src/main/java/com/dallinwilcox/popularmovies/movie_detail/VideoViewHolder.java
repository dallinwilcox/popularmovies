package com.dallinwilcox.popularmovies.movie_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dallinwilcox.popularmovies.R;
import com.dallinwilcox.popularmovies.data.Video;

/**
 * Created by dcwilcox on 12/4/2015.
 */
public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private MovieDetailAdapter movieDetailAdapter;
    private ImageView videoThumbImageView;
    private TextView videoName;


    public VideoViewHolder(MovieDetailAdapter movieDetailAdapter, View videoView) {
        super(videoView);
        this.movieDetailAdapter = movieDetailAdapter;
        videoView.setClickable(true);
        videoView.setOnClickListener(movieDetailAdapter);
        videoThumbImageView = (ImageView) videoView.findViewById(R.id.video_thumb_image_view);
        videoName = (TextView) videoView.findViewById(R.id.video_name_text_view);
    }

    public void bind(int position, Video video) {
        Glide.with(videoThumbImageView.getContext())
                .load(video.getThumbnailUrl())
                .centerCrop()
                .into(videoThumbImageView);
        videoName.setText(video.getName());

    }

    @Override
    public void onClick(View v) {
        if (movieDetailAdapter.getItemClick() != null) {
            movieDetailAdapter.getItemClick().onItemClicked(getAdapterPosition());
            //http://stackoverflow.com/questions/32323548/passing-data-from-on-click-function-of-my-recycler-adaptor
            //http://stackoverflow.com/a/27886776
        }
    }
}
