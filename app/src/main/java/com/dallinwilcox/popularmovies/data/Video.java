package com.dallinwilcox.popularmovies.data;

import android.net.Uri;

/**
 * Created by dcwilcox on 11/9/2015.
 */
public class Video {
    public static final String YOU_TUBE = "YouTube";
    private String id;
    private String iso_639_1;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;

    public String getThumbnailUrl ()
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("img.youtube.com")
                .appendPath("vi")
                .appendPath(key)
                .appendPath("mqdefault.jpg");
        // url looks like http://img.youtube.com/vi/dXo0LextZTU/mqdefault.jpg

        return builder.build().toString();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }
}
