package com.dallinwilcox.popularmovies.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcwilcox on 12/18/2015.
 */
public class FavoriteHelper {

    public static final String TAG = "FavoriteHelper";

    public static Uri saveFavorite(ContentResolver resolver, Movie movie) {
        if (isFavorite(resolver, movie.getId()))
        {
            Log.d(TAG, "movie is already favorite");
            return null;
        }
        Favorite favorite = new Favorite(movie);
        Uri uri = resolver.insert(FavoriteTable.CONTENT_URI, FavoriteTable.getContentValues(favorite, false));
        if (uri != null) {
            Log.d(TAG, "added new favorite");
        }
        return uri;
    }
    public static int deleteFavorite(ContentResolver resolver, int movieId) {
        int result = resolver.delete(FavoriteTable.CONTENT_URI, FavoriteTable.FIELD_MOVIE_ID + "=?", new String[]{String.valueOf(movieId)});
        if (result > 0) {
            Log.d(TAG, "deleted favorite");
        }
        return result;
    }

    //quick query just to check if a movie is already a favorite.
    public static boolean isFavorite(ContentResolver resolver, int movieId)
    {
        Cursor cursor = resolver.query(FavoriteTable.CONTENT_URI, new String[]{"movie_id"}, "movie_id=?", new String[]{Integer.toString(movieId)}, null);
        return cursor.getCount() != 0;
    }

    public static MovieResponse getFavoriteMovies(ContentResolver resolver, int page){
        Cursor cursor = resolver.query(FavoriteTable.CONTENT_URI, null, null, null, null);
        List<Favorite> favoriteRows = FavoriteTable.getRows(cursor, false);
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        Gson gson = new Gson(); //only create this once to avoid allocating unnecessary new objects
        for (Favorite favorite : favoriteRows)
        {
            movieList.add(favorite.getMovie(gson));
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setResults(movieList);
        movieResponse.setPage(1);//TODO implement cursor limit to match page size
        return movieResponse;
    }

    public static void deleteAll(ContentResolver resolver) {
        resolver.delete(FavoriteTable.CONTENT_URI, null, null);
    }
}
