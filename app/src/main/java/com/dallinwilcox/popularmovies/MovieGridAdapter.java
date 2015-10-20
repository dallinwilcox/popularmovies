package com.dallinwilcox.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcwilcox on 10/9/2015.
 * <p/>
 * //referenced <a href="http://www.vogella.com/tutorials/AndroidRecyclerView/article.html#recyclerview_overview">
 * http://www.vogella.com/tutorials/AndroidRecyclerView/article.html#recyclerview_overview</a>
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridViewHolder> {
    private ArrayList<Movie> adapterMovieList;
    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private String apiKey;

    @Override
    public MovieGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cell, parent, false);
        MovieGridViewHolder movieGridViewHolder = new MovieGridViewHolder(cell);
        return movieGridViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieGridViewHolder holder, int position) {
        String posterURL = adapterMovieList.get(position).getQualifiedPosterUrl();
        Glide.with(holder.posterImageView.getContext()).load(posterURL).into(holder.posterImageView);
    }


    @Override
    public int getItemCount() {
        return adapterMovieList.size();
    }

    public class MovieGridViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterImageView;

        public MovieGridViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.poster_image_view);
        }
    }

    public void add(int position, Movie movie){
        adapterMovieList.add(position, movie);
        notifyItemInserted(position);
    }

    public void remove(Movie movie)
    {
        int position = adapterMovieList.indexOf(movie);
        adapterMovieList.remove(position);
        notifyItemRemoved(position);
    }

    public MovieGridAdapter(Context context){
        adapterMovieList = new ArrayList<Movie>();
        queue = Volley.newRequestQueue(context);
        apiKey = context.getString(R.string.tmdb_api_key);
        fireRequest(1);//initial call for first page
    }
    private void fireRequest(int page){

        //reference http://stackoverflow.com/questions/19167954/use-uri-builder-in-android-or-create-url-with-variables
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("sorty_by", "popularity.desc")
                .appendQueryParameter("api_key", apiKey);
        //        http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[YOUR API KEY]
        String url = builder.build().toString();
        Log.d("request", url);
        // Request a string response from the provided URL
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", "Response is: " + response);

                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response","ErrorResponse");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    private void parseResponse(String response) {
        //referenced http://stackoverflow.com/questions/8650913/gson-deserializer-for-java-util-date
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Type movieListType = new TypeToken<MovieResponse>(){}.getType();
        MovieResponse movieResponse = gson.fromJson(response, movieListType);
        adapterMovieList.addAll(movieResponse.getResults());
        notifyItemRangeChanged(movieResponse.getPage(), movieResponse.getResultSize());
    }
}
