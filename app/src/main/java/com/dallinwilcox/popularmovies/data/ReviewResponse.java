package com.dallinwilcox.popularmovies.data;

import java.util.ArrayList;

/**
 * Created by dcwilcox on 11/9/2015.
 */
public class ReviewResponse {
    private int id;
    private int page;
    private ArrayList<Review> results;
    private int total_pages;
    private int total_results;
    public ArrayList<Review> getResults() {
        return results;
    }
    public int getResultSize()
    {
        return results.size();
    }
}
