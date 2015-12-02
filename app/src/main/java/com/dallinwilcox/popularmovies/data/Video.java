package com.dallinwilcox.popularmovies.data;

import android.net.Uri;

/**
 * Created by dcwilcox on 11/9/2015.
 */
public class Video {
    String id;
    String iso_639_1;
    String key;
    String name;
    String site;
    String size;
    String type;

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
}
