package com.dallinwilcox.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI; //GSON speaks java.net URI so using that for parsing
import java.util.Date;

/**
 * Created by dcwilcox on 10/13/2015.
 */
public class Movie implements Parcelable{
    boolean adult;
    URI backdrop_path;
    int[] genre_ids;
    int id;  //if id ever exceeds max int, may need to switch to a long
    String original_language;
    String original_title;
    String overview;
    Date release_date;
    URI poster_path;
    float popularity;
    String title;
    boolean video;
    float vote_average;
    float vote_count;

    protected Movie(Parcel in) {
        adult = in.readByte() != 0;
        genre_ids = in.createIntArray();
        id = in.readInt();
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        popularity = in.readFloat();
        title = in.readString();
        video = in.readByte() != 0;
        vote_average = in.readFloat();
        vote_count = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeIntArray(genre_ids);
        dest.writeInt(id);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeFloat(popularity);
        dest.writeString(title);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeFloat(vote_average);
        dest.writeFloat(vote_count);
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

    public String getPosterPath()
    {
        return poster_path.toString();
    }
}
