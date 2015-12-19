package com.dallinwilcox.popularmovies.data;

import com.google.gson.Gson;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by dcwilcox on 12/18/2015.
 */

//referenced https://github.com/ckurtm/simple-sql-provider
    //I'm too lazy to map all the fields for Movie, so I use gson to handle recreating the objects
    //and store the id separately for indexing and querying purposes without recreating every time.
@SimpleSQLTable(table = "favorite", provider = FavoriteProviderConfig.FAVORITE_PROVIDER)
public class Favorite {
    @SimpleSQLColumn("movie_id")
    public int movieId;
    //using JSON to stuff the whole object into the db.
    @SimpleSQLColumn("movie_json")
    public String movieJson;

    public Favorite(Movie movie) {
        this.movieId = movie.getId();
        Gson gson = new Gson();
        this.movieJson = gson.toJson(movie);
    }

    public Movie getMovie(Gson gson) {
        return gson.fromJson(movieJson, Movie.class);
    }
}
