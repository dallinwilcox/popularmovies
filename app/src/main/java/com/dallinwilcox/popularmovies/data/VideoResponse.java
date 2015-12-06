package com.dallinwilcox.popularmovies.data;

import java.util.ArrayList;

/**
 * Created by dcwilcox on 11/9/2015.
 */
public class VideoResponse {
    private int id;
    private ArrayList<Video> results;
    public ArrayList<Video> getResults() {
        return results;
    }
    //if only Android supported Java 8 default methods in an interface...
    public int getResultSize()
    {
        return results.size();
    }
}
