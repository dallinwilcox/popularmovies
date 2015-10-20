package com.dallinwilcox.popularmovies;

import java.util.ArrayList;

/**
 * Created by dcwilcox on 10/19/2015.
 */
public class MovieResponse {
    int page;

    public int getPage() {
        return page;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public int getResultSize()
    {
        return results.size();
    }
    ArrayList<Movie> results;
}
