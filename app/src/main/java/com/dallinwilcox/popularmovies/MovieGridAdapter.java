package com.dallinwilcox.popularmovies;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.bumptech.glide.Glide;
import com.dallinwilcox.popularmovies.data.FavoriteHelper;
import com.dallinwilcox.popularmovies.data.Movie;
import com.dallinwilcox.popularmovies.data.MovieResponse;
import com.dallinwilcox.popularmovies.inf.AutofitRecyclerView;
import com.dallinwilcox.popularmovies.inf.OnItemClick;
import com.dallinwilcox.popularmovies.inf.RequestManager;
import com.dallinwilcox.popularmovies.inf.RequestUtils;
import com.dallinwilcox.popularmovies.settings.SettingsFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by dcwilcox on 10/9/2015.
 * <p/>
 * //referenced <a href="http://www.vogella.com/tutorials/AndroidRecyclerView/article.html#recyclerview_overview">
 * http://www.vogella.com/tutorials/AndroidRecyclerView/article.html#recyclerview_overview</a>
 */
public class MovieGridAdapter extends AutofitRecyclerView.Adapter<MovieGridAdapter.MovieGridViewHolder>
    implements SharedPreferences.OnSharedPreferenceChangeListener {
    private ArrayList<Movie> adapterMovieList;
    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private String apiKey = "";
    private String sortBy = "";
    private OnItemClick itemClick;
    private ContentResolver contentResolver;


    @Override
    public MovieGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cell, parent, false);
        return new MovieGridViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(MovieGridViewHolder holder, int position) {
        Context context = holder.posterImageView.getContext();
        Glide.with(context)
                .load(adapterMovieList.get(position).getPosterUrl())
                .centerCrop()
                .into(holder.posterImageView);
    }


    @Override
    public int getItemCount() {
        return adapterMovieList.size();
    }

    //not sure why, but this isn't getting called.  Suspected it was due to unregistering in onPause
    //which would fire when settings is displayed, but changed from onResume/onPause to
    //onCreate/onStop and it's still happening.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        //don't really care which one changed, need to regenerate the combination of both
        //preferences to check for changes to the view
        String storedSortPreferences = getStoredSortPreferences(sharedPreferences);

        //if new string matches the old one, don't need to do anything
        if (sortBy.equals(storedSortPreferences))
        {
            return;
        }
        //Implicit else
        sortBy = storedSortPreferences;

        int endPositionOfItemsToRemove = adapterMovieList.size() - 1 ;
        adapterMovieList.clear();
        notifyDataSetChanged();
        requestMovies(1);
    }

    @NonNull
    private String getStoredSortPreferences(SharedPreferences sharedPreferences) {
        //considered doing some validation, but since everything is coming from resources,
        //chances are really low that we'll get bogus values

        String sortString = new StringBuilder(
            sharedPreferences.getString(SettingsFragment.PREF_SORT_BY_KEY, ""))
                .append(".")
                .append(sharedPreferences.getString(SettingsFragment.PREF_SORT_ORDER_KEY, ""))
                .toString();

        Log.d("MovieGridAdapter", "sortString" + sortString);
        return sortString;
    }

    public class MovieGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView posterImageView;

        public MovieGridViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            posterImageView = (ImageView) itemView.findViewById(R.id.poster_image_view);
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

    public Movie get(int position)
    {
        return adapterMovieList.get(position);
    }

    public MovieGridAdapter(Context context){
        adapterMovieList = new ArrayList<Movie>();
        queue = RequestManager.getInstance(context).getRequestQueue();
        apiKey = context.getString(R.string.tmdb_api_key);
        contentResolver = context.getContentResolver();
        //don't have access to getDefaultSharedPreferences, so recreating instead of adding
        //an additional parameter since we can get it from context that is already passed in
        //see http://stackoverflow.com/a/6310080
        sortBy = getStoredSortPreferences(
                context.getSharedPreferences(
                        context.getPackageName() + "_preferences", Context.MODE_PRIVATE));

        if (adapterMovieList.size() == 0)
        {
            requestMovies(1);//initial call for first page
        }
    }
    private void requestMovies(int page){

        if (sortBy.startsWith("favorite"))
        {
            MovieResponse movieResponse = FavoriteHelper.getFavoriteMovies( contentResolver, 1);
            adapterMovieList.addAll(movieResponse.getResults());
            notifyItemRangeInserted((0), movieResponse.getResultSize());

            return; //no need to make calls for favorites
        }
        //reference http://stackoverflow.com/questions/19167954/use-uri-builder-in-android-or-create-url-with-variables
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("vote_count.gte", Integer.toString(50))
                .appendQueryParameter("page", Integer.toString(page))
                .appendQueryParameter("year", "2015")
                .appendQueryParameter("sorty_by", sortBy)
                .appendQueryParameter("api_key", apiKey);
        //        http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[YOUR API KEY]
        String url = builder.build().toString();
        Log.v("request", url);
        // Request a string response from the provided URL
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", "Response is: " + response);

                        parseResponse(response);
                    }
                }, new RequestUtils.DefaultErrorResponseListener()
        );
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
        //save current size as insertion position for notification before adding new results
        //works well since size = current last position + 1 which is where the first
        // new item is inserted and handles zero case
        int insertionPosition = adapterMovieList.size();
        adapterMovieList.addAll(movieResponse.getResults());
        notifyItemRangeInserted((insertionPosition), movieResponse.getResultSize());
    }

}
