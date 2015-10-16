package com.dallinwilcox.popularmovies;

import android.net.Uri; //using for convenience with Uri.builder
import java.net.URI; //GSON speaks java.net URI so using that for parsing
import java.util.Date;
import java.util.List;

/**
 * Created by dcwilcox on 10/13/2015.
 */
public class Movie {
    boolean adult;
    URI backdrop_path;
    int[] genre_ids;
    int id;  //if ids ever exceed max int, may need to switch to a long
    String original_language;
    String original_title;
    String overview;
    Date release_date;
    URI poster_path;
    float popularity;
    String title;
    boolean video;
    float vote_average;
    float vote_count;

    public String getQualifiedPosterUrl()
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendEncodedPath("/")
                .appendEncodedPath(poster_path.toString());
        //        http://image.tmdb.org/t/p/w185//[poster_path]
        return builder.build().toString();
    }
}
