package com.btc.linear.bookstoretest.Entities;

/**
 * Created by Ben on 05/05/2018.
 */

/* Author class - relational supporting class to the book class */
public class Author {
    public static final String TABLE = "Author";

    public static final String KEY_ID = "availability_id";
    public static final String KEY_AUTHOR_FULL_NAME = "author_full_name";

    private int author_ID;
    private String authorFullName;

    public int getAuthor_ID() {
        return author_ID;
    }

    public void setAuthor_ID(int author_ID) {
        this.author_ID = author_ID;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }

    /* Constructor to instantiate new object with data */
    public Author(String FullName){
        this.authorFullName = FullName;
    }
}
