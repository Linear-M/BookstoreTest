package com.btc.linear.bookstoretest.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.btc.linear.bookstoretest.Entities.Author;
import com.btc.linear.bookstoretest.Entities.Availability;
import com.btc.linear.bookstoretest.Entities.Book;
import com.btc.linear.bookstoretest.Entities.Format;
import com.btc.linear.bookstoretest.Entities.Genre;
import com.btc.linear.bookstoretest.Entities.User;

/**
 * Created by Ben on 12/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 22;

    public static final String DATABASE_NAME = "BookstoreTest.db";

    /* Constructor to gain context and set super's*/
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Generate User, Book, Genre, Format, Availability and Author tables on app first load/version increment */
    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE + "("
                + User.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + User.KEY_Username + " TEXT, "
                + User.KEY_Email + " TEXT, "
                + User.KEY_Location + " TEXT, "
                + User.KEY_Referral_Code + " TEXT, "
                + User.KEY_Password + " TEXT )";

        String CREATE_TABLE_BOOK = "CREATE TABLE " + Book.TABLE + "("
                + Book.KEY_ISBN + " BIGINT PRIMARY KEY, "
                + Book.KEY_Name + " TEXT, "
                + Book.KEY_Author + " TEXT, "
                + Book.KEY_Price + " DECIMAL, "
                + Book.KEY_ReleaseDate + " TEXT, "
                + Book.KEY_Format + " TEXT, "
                + Book.KEY_Review + " SMALLINT, "
                + Book.KEY_PictureURL + " TEXT, "
                + Book.KEY_Description + " TEXT, "
                + Book.KEY_Genre + " TEXT, "
                + Book.KEY_Availability + " TEXT )";

        String CREATE_TABLE_GENRE = "CREATE TABLE " + Genre.TABLE + "("
                + Genre.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Genre.KEY_GENRE_TYPE + " TEXT, "
                + Genre.KEY_GENRE_DESCRIPTION + " TEXT )";

        String CREATE_TABLE_FORMAT = "CREATE TABLE " + Format.TABLE + "("
                + Format.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Format.KEY_FORMAT_TYPE + " TEXT )";

        String CREATE_TABLE_AVAILABILITY = "CREATE TABLE " + Availability.TABLE + "("
                + Availability.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Availability.KEY_AVAILABILITY_TYPE + " TEXT )";

        String CREATE_TABLE_AUTHOR = "CREATE TABLE " + Author.TABLE + "("
                + Author.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Author.KEY_AUTHOR_FULL_NAME + " TEXT )";

        database.execSQL(CREATE_TABLE_USER);
        database.execSQL(CREATE_TABLE_BOOK);
        database.execSQL(CREATE_TABLE_GENRE);
        database.execSQL(CREATE_TABLE_FORMAT);
        database.execSQL(CREATE_TABLE_AVAILABILITY);
        database.execSQL(CREATE_TABLE_AUTHOR);
    }

    /* On DB version increment, drop both tables and re-create */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + Book.TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + Genre.TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + Format.TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + Availability.TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + Author.TABLE);

        onCreate(database);
    }
}
