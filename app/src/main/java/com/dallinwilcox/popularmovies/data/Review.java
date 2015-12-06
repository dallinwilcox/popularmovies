package com.dallinwilcox.popularmovies.data;

import java.net.URL;

/**
 * Created by dcwilcox on 11/9/2015.
 */
public class Review {
    private String id;
    private String author;
    private String content;
    private URL url;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public URL getUrl() {
        return url;
    }
}
