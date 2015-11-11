package com.dallinwilcox.popularmovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.dallinwilcox.popularmovies.data.Movie;

import com.dallinwilcox.popularmovies.data.Video;
import com.dallinwilcox.popularmovies.data.VideoResponse;
import com.dallinwilcox.popularmovies.inf.RequestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

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
    private void requestVideos(int id)
    {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(Integer.toString(id))
                .appendPath("videos")
                .appendQueryParameter("api_key", getString(R.string.tmdb_api_key));
            // http://api.themoviedb.org/3/movie/{id}/videos?api_key=[YOUR API KEY]
        String url = builder.build().toString();
        Log.v("request", url);
        // Request a string response from the provided URL
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", "Response is: " + response);

                        parseVideoResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response","ErrorResponse");
            }
        });
        // Add the request to the RequestQueue.
        RequestManager.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void parseVideoResponse(String response) {
        //referenced http://stackoverflow.com/questions/8650913/gson-deserializer-for-java-util-date
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Type videoListType = new TypeToken<VideoResponse>(){}.getType();
        VideoResponse videoResponse = gson.fromJson(response, videoListType);
        //TODO do something with VideoResponse
    }

    private void requestReviews()
    {
        // http://api.themoviedb.org/3/movie/{id}/reviews?api_key=[YOUR API KEY]
        //// TODO: 11/10/2015 implement me!
    }

}
