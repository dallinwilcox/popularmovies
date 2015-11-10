package com.dallinwilcox.popularmovies.data;

import java.util.ArrayList;

/**
 * Created by dcwilcox on 11/9/2015.
 */
public class VideoResponse {
    int id;
    ArrayList<Video> results;
    public ArrayList<Video> getResults() {
        return results;
    }
}
