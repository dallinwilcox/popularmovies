package com.dallinwilcox.popularmovies.data;

import android.net.Uri;  //Using android.net Uri for url construction
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI; //GSON speaks java.net URI so using that for parsing
import java.text.SimpleDateFormat;
import java.util.Date;

import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by dcwilcox on 10/13/2015.
 */
public class Movie implements Parcelable{
    boolean adult;
    //field names match API JSON object field names for Gson parsing so using _ instead of camelcase
    private URI backdrop_path;
    private int[] genre_ids;
    private int id;  //if id ever exceeds max int, may need to switch to a long
    private String original_language;
    private String original_title;
    private String overview;
    private Date release_date;
    private URI poster_path;
    private float popularity;
    private String title;
    private boolean video;
    private float vote_average;
    private float vote_count;
    transient private boolean favorite = false;

    protected Movie(Parcel in) {
        adult = in.readByte() != 0;
        backdrop_path = (URI.create(in.readString()));
        genre_ids = in.createIntArray();
        id = in.readInt();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        //referenced http://stackoverflow.com/questions/21017404/reading-and-writing-java-util-date-from-parcelable-class
        release_date = new Date(in.readLong());
        poster_path = (URI.create(in.readString()));
        popularity = in.readFloat();
        title = in.readString();
        video = in.readByte() != 0;
        vote_average = in.readFloat();
        vote_count = in.readFloat();
        favorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdrop_path.toString());
        dest.writeIntArray(genre_ids);
        dest.writeInt(id);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeLong(release_date.getTime());
        dest.writeString(poster_path.toString());
        dest.writeFloat(popularity);
        dest.writeString(title);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeFloat(vote_average);
        dest.writeFloat(vote_count);
        dest.writeByte((byte) (favorite ? 1 :0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getPosterUrl()
    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                //.appendPath("w" + context.getResources().getDimensionPixelSize(R.dimen.poster_width))
                .appendEncodedPath(poster_path.toString());
        // url looks like http://image.tmdb.org/t/p/w185//[poster_path]

        return builder.build().toString();
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getFormattedReleaseDate() {
       //reference http://developer.android.com/reference/java/text/SimpleDateFormat.html
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM yyyy");
       return dateFormatter.format(release_date);
    }

    public float getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public float getVote_count() {
        return vote_count;
    }
}
