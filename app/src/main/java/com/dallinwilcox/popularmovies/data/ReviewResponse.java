package com.dallinwilcox.popularmovies.data;

import java.util.ArrayList;

/**
 * Created by dcwilcox on 11/9/2015.
 */
public class ReviewResponse {
    int id;
    int page;
    ArrayList<Review> results;
    int total_pages;
    int total_results;
    public ArrayList<Review> getResults() {
        return results;
    }
    public int getResultSize()
    {
        return results.size();
    }
}
