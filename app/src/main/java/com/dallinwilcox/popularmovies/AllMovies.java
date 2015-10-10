package com.dallinwilcox.popularmovies;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import android.support.v17.leanback.widget.VerticalGridView;

import org.json.JSONException;
import org.json.JSONObject;

public class AllMovies extends AppCompatActivity {
    ImageView imageView;
    VerticalGridView movieGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);
        fireRequest();
        imageView = (ImageView) findViewById(R.id.test_image_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fireRequest(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //reference http://stackoverflow.com/questions/19167954/use-uri-builder-in-android-or-create-url-with-variables
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("sorty_by", "popularity.desc")
                .appendQueryParameter("api_key", getString(R.string.tmdb_api_key));
                    //        http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[YOUR API KEY]
                    String url = builder.build().toString();
                    Log.d("request", url);
                    // Request a string response from the provided URL
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", "Response is: " + response.toString() );

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

    private void parseResponse(JSONObject response) {
        String posterPath = "";
        try {
            posterPath = response.getJSONArray("results").getJSONObject(0).getString("poster_path");
        } catch (JSONException e) {
            Log.e("JSONException", "exception", e);
        }
        Log.v("poster_path", posterPath);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendEncodedPath("/")
                .appendEncodedPath(posterPath);
        //        http://image.tmdb.org/t/p/w185//[poster_path]
        String url = builder.build().toString();
        Log.v("posterUrl", url);
        Glide.with(this).load(url).into(imageView);

    }
}
