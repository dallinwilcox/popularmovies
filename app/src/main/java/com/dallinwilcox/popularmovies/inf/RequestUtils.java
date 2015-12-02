package com.dallinwilcox.popularmovies.inf;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by dcwilcox on 12/1/2015.
 */
public class RequestUtils {


    public static StringRequest buildMovieDetailRequest(int movieId, String action, String apiKey, Response.Listener responseListener) {
        return buildMovieDetailRequest(movieId, action, apiKey, responseListener, new DefaultErrorResponseListener());
    }

    public static StringRequest buildMovieDetailRequest(int movieId, String action, String apiKey, Response.Listener responseListener, Response.ErrorListener errorResponseListener){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(Integer.toString(movieId))
                .appendPath(action)
                .appendQueryParameter("api_key", apiKey);
        //videos url - http://api.themoviedb.org/3/movie/{id}/videos?api_key=[YOUR API KEY]
        //review url - http://api.themoviedb.org/3/movie/{id}/reviews?api_key=[YOUR API KEY]
        String url = builder.build().toString();
        Log.v("request", url);
        // Request a string response from the provided URL
        return new StringRequest(Request.Method.GET, url, responseListener, errorResponseListener);
    }

    public static class DefaultErrorResponseListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("response", "ErrorResponse" + error.getMessage(), error.getCause());
        }
    }
}
