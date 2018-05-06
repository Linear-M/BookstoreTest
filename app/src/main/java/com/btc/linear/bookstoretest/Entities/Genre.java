package com.btc.linear.bookstoretest.Entities;

/**
 * Created by Ben on 05/05/2018.
 */

/* Genre class - relational supporting class to the book class */
public class Genre {
    public static final String TABLE = "Genre";

    public static final String KEY_ID = "genre_id";
    public static final String KEY_GENRE_TYPE = "genre_type";
    public static final String KEY_GENRE_DESCRIPTION = "genre_description";

    private int genre_ID;
    private String genreType;
    private String genreDescription;

    public int getGenre_ID() {
        return genre_ID;
    }

    public void setGenre_ID(int genre_ID) {
        this.genre_ID = genre_ID;
    }

    public String getGenreType() {
        return genreType;
    }

    public void setGenreType(String genreType) {
        this.genreType = genreType;
    }

    public String getGenreDescription() {
        return genreDescription;
    }

    public void setGenreDescription(String genreDescription) {
        this.genreDescription = genreDescription;
    }

    /* Constructor to instantiate new object with data */
    public Genre(String Type, String Description){
        this.genreType = Type;
        this.genreDescription = Description;
    }
}
