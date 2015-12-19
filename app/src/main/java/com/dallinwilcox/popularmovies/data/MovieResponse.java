package com.dallinwilcox.popularmovies.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcwilcox on 10/19/2015.
 */
public class MovieResponse {
    private int page;
    ArrayList<Movie> results;

    public void setResults(List<Movie> response) {
       this.results = results;
    }

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

    public void setPage(int page) {
        this.page = page;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }
}
