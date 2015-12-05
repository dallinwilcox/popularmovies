package com.dallinwilcox.popularmovies.movie_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.dallinwilcox.popularmovies.R;
import com.dallinwilcox.popularmovies.data.Movie;
import com.dallinwilcox.popularmovies.data.Review;
import com.dallinwilcox.popularmovies.data.ReviewResponse;
import com.dallinwilcox.popularmovies.data.Video;
import com.dallinwilcox.popularmovies.data.VideoResponse;
import com.dallinwilcox.popularmovies.inf.OnItemClick;
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
 * <a href="https://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView">
 * https://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView</a>
 */
public class MovieDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
// TODO: 12/3/2015 Need to decide on number of lists (1 or 2 + single movie object?)
    private ArrayList<Object> adapterItems;
//    private ArrayList<Video> adapterVideoList;
    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private String apiKey = "";
    private Movie movie;
    private int numVideos = 0;
    private OnItemClick itemClick;
    private static final int VIEW_TYPE_MOVIE = 0;
    private static final int VIEW_TYPE_VIDEO = 1;
    private static final int VIEW_TYPE_REVIEW = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_REVIEW:
                View review = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.review_cell, parent, false);
                return new ReviewViewHolder(review);
            case VIEW_TYPE_VIDEO:
                View video = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.video_cell, parent, false);
                return new VideoViewHolder(this, video);
            case VIEW_TYPE_MOVIE:
            default:
                View overview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.overview_cell, parent, false);
                return new MovieOverviewViewHolder(overview);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        position--;//account for movie overview
        switch (holder.getItemViewType()){
            case VIEW_TYPE_REVIEW:
                ReviewViewHolder reviewViewHolder = (ReviewViewHolder) holder;
                reviewViewHolder.bind(position, (Review) adapterItems.get(position));
                break;
            case VIEW_TYPE_VIDEO:
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                videoViewHolder.bind(position, (Video) adapterItems.get(position));
                break;
            case VIEW_TYPE_MOVIE:
            default:
                MovieOverviewViewHolder overviewHolder = (MovieOverviewViewHolder) holder;
                overviewHolder.bind(position, movie);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return adapterItems.size() + 1; //account for movie overview
    }

    @Override
    public int getItemViewType(int position) {
        position--; //account for movie overview
        if (position < 0)
        {
            return VIEW_TYPE_MOVIE;
        } else if (adapterItems.get(position) instanceof Video) {
            return VIEW_TYPE_VIDEO;
        } else if (adapterItems.get(position) instanceof Review){
            return VIEW_TYPE_REVIEW;
        }
        return -1;
    }

    public OnItemClick getItemClick() {
        return itemClick;
    }

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public MovieDetailAdapter(Context context, Movie movie) {
        adapterItems = new ArrayList<>();
        queue = RequestManager.getInstance(context).getRequestQueue();
        apiKey = context.getString(R.string.tmdb_api_key);
        this.movie = movie;
        requestVideos();
        requestReviews();
    }

    private void requestVideos() {
        queue.add( RequestUtils.buildMovieDetailRequest(
                    movie.getId(),
                    "videos",
                    apiKey,
                    new VideoResponseListener()));
    }

    private void requestReviews() {
        queue.add(RequestUtils.buildMovieDetailRequest(
                movie.getId(),
                "reviews",
                apiKey,
                new ReviewResponseListener()));
    }

    @Override
    public void onClick(View v) {

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
        Type videoListType = new TypeToken<VideoResponse>(){}.getType();
        VideoResponse videoResponse = gson.fromJson(response, videoListType);
        //always insert videos at the front so they render first after the overview.
        adapterItems.addAll(0, videoResponse.getResults());
        notifyItemRangeInserted(0, videoResponse.getResultSize());
    }

    private class ReviewResponseListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            Log.v("response", "Response is: " + response);
            parseReviewResponse(response);
        }
    }

    private void parseReviewResponse(String response) {
        // /referenced http://stackoverflow.com/questions/8650913/gson-deserializer-for-java-util-date
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Type reviewListType = new TypeToken<ReviewResponse>(){}.getType();
        ReviewResponse reviewResponse = gson.fromJson(response, reviewListType);
        //always insert reviews at the back so they render last after the overview and videos.
        adapterItems.addAll(reviewResponse.getResults());
        notifyItemRangeInserted(0, reviewResponse.getResultSize());
    }
}