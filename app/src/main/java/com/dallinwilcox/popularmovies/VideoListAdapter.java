package com.dallinwilcox.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.dallinwilcox.popularmovies.data.Video;
import com.dallinwilcox.popularmovies.data.VideoResponse;
import com.dallinwilcox.popularmovies.inf.RequestManager;
import com.dallinwilcox.popularmovies.inf.RequestUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by dcwilcox on 12/1/2015.
 * <p/>
 * //referenced <a href="http://www.vogella.com/tutorials/AndroidRecyclerView/article.html#recyclerview_overview">
 * http://www.vogella.com/tutorials/AndroidRecyclerView/article.html#recyclerview_overview</a>
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> {
    private ArrayList<Video> adapterVideoList;
    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private String apiKey = "";
    private int movieId;
    private OnItemClick itemClick;

    @Override
    public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_cell, parent, false);
        return new VideoListViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(VideoListViewHolder holder, int position) {
        Video videoAtPosition = adapterVideoList.get(position);
        Glide.with(holder.videoThumbImageView.getContext())
                .load(videoAtPosition.getThumbnailUrl())
                .centerCrop()
                .into(holder.videoThumbImageView);
        holder.videoName.setText(videoAtPosition.getName());
    }


    @Override
    public int getItemCount() {
        return adapterVideoList.size();
    }

    public class VideoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView videoThumbImageView;
        public TextView videoName;

        public VideoListViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            videoThumbImageView = (ImageView) itemView.findViewById(R.id.video_thumb_image_view);
            videoName =(TextView) itemView.findViewById(R.id.video_name_text_view);
        }

        @Override
        public void onClick(View v) {
            if (itemClick != null) {
                itemClick.onItemClicked(getAdapterPosition());
                //http://stackoverflow.com/questions/32323548/passing-data-from-on-click-function-of-my-recycler-adaptor
                //http://stackoverflow.com/a/27886776
            }
        }
    }

    public OnItemClick getItemClick() {
        return itemClick;
    }

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void add(int position, Video video) {
        adapterVideoList.add(position, video);
        notifyItemInserted(position);
    }

    public void remove(Video video) {
        int position = adapterVideoList.indexOf(video);
        adapterVideoList.remove(position);
        notifyItemRemoved(position);
    }

    public Video get(int position) {
        return adapterVideoList.get(position);
    }

    public VideoListAdapter(Context context, int movieId) {
        adapterVideoList = new ArrayList<Video>();
        queue = RequestManager.getInstance(context).getRequestQueue();
        apiKey = context.getString(R.string.tmdb_api_key);
        this.movieId = movieId;

        if (adapterVideoList.size() == 0) {
            requestVideos();
        }
    }

    private void requestVideos() {
        // Add the request to the RequestQueue.
        queue.add(
                RequestUtils.buildMovieDetailRequest(movieId, "videos", apiKey, new VideoResponseListener()));
    }

    private class VideoResponseListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            Log.v("response", "Response is: " + response);

            parseVideoResponse(response);
        }
    }

    private void parseVideoResponse(String response) {
        //referenced http://stackoverflow.com/questions/8650913/gson-deserializer-for-java-util-date
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Type videoListType = new TypeToken<VideoResponse>() {
        }.getType();
        VideoResponse videoResponse = gson.fromJson(response, videoListType);
        int insertionPosition = adapterVideoList.size();
        adapterVideoList.addAll(videoResponse.getResults());
        notifyItemRangeInserted((insertionPosition), videoResponse.getResultSize());
    }
}