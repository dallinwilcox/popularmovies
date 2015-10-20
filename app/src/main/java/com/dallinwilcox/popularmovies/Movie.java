package com.dallinwilcox.popularmovies;

import java.net.URI; //GSON speaks java.net URI so using that for parsing
import java.util.Date;

/**
 * Created by dcwilcox on 10/13/2015.
 */
public class Movie {
    boolean adult;
    URI backdrop_path;
    int[] genre_ids;
    int id;  //if id ever exceeds max int, may need to switch to a long
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

    public String getPosterPath()
    {
        return poster_path.toString();
    }
}
