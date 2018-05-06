package com.btc.linear.bookstoretest.Entities;

/**
 * Created by Ben on 05/05/2018.
 */
public class Genre {
    public static final String TABLE = "Genre";

    public static final String KEY_ID = "genre_id";
    public static final String KEY_GENRE_TYPE = "genre_type";
    public static final String KEY_GENRE_DESCRIPTION = "genre_description";

    public int genre_ID;
    public String genreType;
    public String genreDescription;

    public Genre(String Type, String Description){
        this.genreType = Type;
        this.genreDescription = Description;
    }
}
