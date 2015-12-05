package com.dallinwilcox.popularmovies.movie_detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.dallinwilcox.popularmovies.R;
import com.dallinwilcox.popularmovies.data.Movie;

import com.dallinwilcox.popularmovies.data.ReviewResponse;
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
    @Bind(R.id.titleText)
    TextView titleText;
    @Bind(R.id.posterImageView)
    ImageView posterImage;
    @Bind(R.id.releaseDateText)
    TextView releaseDate;
    @Bind(R.id.overviewText)
    TextView overviewText;
    @Bind(R.id.voteAverageText)
    TextView rating;
    @Bind(R.id.detail_recycler)
    RecyclerView videos;
//    @Bind(R.id.reviews)
//    RecyclerView reviews;
    //referenced http://jakewharton.github.io/butterknife/
    private MovieDetailAdapter movieDetailAdapter;
//    private ReviewListAdapter reviewListAdapter;

    public static final String MOVIE_EXTRA = "MovieExtra";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Movie movie = (Movie) getIntent().getExtras().getParcelable(MOVIE_EXTRA);
        movieDetailAdapter = new MovieDetailAdapter(this, movie);
        videos.setLayoutManager(new LinearLayoutManager(this));
        videos.setAdapter(movieDetailAdapter);

        //requestReviews(movie.getId());
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


//    private void requestReviews(int id) {
//        requestAction(id, "reviews", new ReviewResponseListener());
//        // http://api.themoviedb.org/3/movie/{id}/reviews?api_key=[YOUR API KEY]
//        //// TODO: 11/10/2015 implement me!
//    }



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
        //TODO do something with ReviewResponse )
    }
}
