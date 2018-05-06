package com.btc.linear.bookstoretest.Entities;

/**
 * Created by Ben on 05/05/2018.
 */
public class Author {
    public static final String TABLE = "Author";

    public static final String KEY_ID = "availability_id";
    public static final String KEY_AUTHOR_FULL_NAME = "author_full_name";

    public int author_ID;
    public String authorFullName;

    public Author(String FullName){
        this.authorFullName = FullName;
    }
}
