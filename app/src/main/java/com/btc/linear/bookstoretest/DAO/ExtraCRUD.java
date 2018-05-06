package com.btc.linear.bookstoretest.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.btc.linear.bookstoretest.Database.DatabaseHelper;
import com.btc.linear.bookstoretest.Entities.Author;
import com.btc.linear.bookstoretest.Entities.Availability;
import com.btc.linear.bookstoretest.Entities.Book;
import com.btc.linear.bookstoretest.Entities.Format;
import com.btc.linear.bookstoretest.Entities.Genre;
import com.btc.linear.bookstoretest.Entities.User;

import java.text.Normalizer;

/**
 * Created by Ben on 05/05/2018.
 */

/* DAO for relational-supporting tables of the Book table - support for genre, format, availability and author */
public class ExtraCRUD {
    private DatabaseHelper databaseHelper;
    private Context context;

    /* Constructor to gain context (mainly for toast calls) */
    public ExtraCRUD(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    /* Add new genre to genre table */
    public long addGenre(Genre genre) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        /* Add genre data to query */
        contentValues.put(Genre.KEY_GENRE_TYPE, genre.getGenreType());
        contentValues.put(Genre.KEY_GENRE_DESCRIPTION, genre.getGenreDescription());

        return database.insert(Genre.TABLE, null, contentValues);
    }

    /* Add new format to format table */
    public long addFormat(Format format) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        /* Add format data to query */
        contentValues.put(Format.KEY_FORMAT_TYPE, format.getFormatType());

        return database.insert(Format.TABLE, null, contentValues);
    }

    /* Add new availability to availability table */
    public long addAvailability(Availability availability) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        /* Add availability data to query */
        contentValues.put(Availability.KEY_AVAILABILITY_TYPE, availability.getAvailabilityType());

        return database.insert(Availability.TABLE, null, contentValues);
    }

    /* Add new author to author table */
    public long addAuthor(Author author) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        /* Add author data to query */
        contentValues.put(Author.KEY_AUTHOR_FULL_NAME, author.getAuthorFullName());

        return database.insert(Author.TABLE, null, contentValues);
    }

    /* Does the genre string exist within the genre table - i.e, is it valid? */
    public boolean doesGenreExist(String genre) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT * FROM " + Genre.TABLE + " WHERE " + Genre.KEY_GENRE_TYPE + "=?";

        /* If can move to first result, a result exists, therefore it goes exist */
        return database.rawQuery(getUserPasswordQuery, new String[]{genre}).moveToFirst() ? true : false;
    }

    /* Does the format string exist within the genre table - i.e, is it valid? */
    public boolean doesFormatExist(String format) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT * FROM " + Format.TABLE + " WHERE " + Format.KEY_FORMAT_TYPE + "=?";

        /* If can move to first result, a result exists, therefore it goes exist */
        return database.rawQuery(getUserPasswordQuery, new String[]{format}).moveToFirst() ? true : false;
    }

    /* Does the availability string exist within the genre table - i.e, is it valid? */
    public boolean doesAvailabilityExist(String availability) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT * FROM " + Availability.TABLE + " WHERE " + Availability.KEY_AVAILABILITY_TYPE + "=?";

        /* If can move to first result, a result exists, therefore it goes exist */
        return database.rawQuery(getUserPasswordQuery, new String[]{availability}).moveToFirst() ? true : false;
    }

    /* Does the genre string exist within the genre table - i.e, is it valid? */
    public boolean doesAuthorExist(String authorFullName) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT * FROM " + Author.TABLE + " WHERE " + Author.KEY_AUTHOR_FULL_NAME + "=?";

        /* If can move to first result, a result exists, therefore it goes exist */
        return database.rawQuery(getUserPasswordQuery, new String[]{authorFullName}).moveToFirst() ? true : false;
    }

    /* Use a simple default-modified SQL query to return genre information */
    public Cursor getListOfGenres(){
        String selectQuery = "SELECT * FROM " + Genre.TABLE;

        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(selectQuery, null);

        //Check if cursor is null and that it is closed prior to return
        if (cursor == null){
            return null;
        } else if (!cursor.moveToFirst()){
            cursor.close();
        }
        return cursor;
    }

    /* Use a simple default-modified SQL query to return format information */
    public Cursor getListOfFormats(){
        String selectQuery = "SELECT * FROM " + Format.TABLE;

        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(selectQuery, null);

        //Check if cursor is null and that it is closed prior to return
        if (cursor == null){
            return null;
        } else if (!cursor.moveToFirst()){
            cursor.close();
        }
        return cursor;
    }

    /* Use a simple default-modified SQL query to return format information */
    public Cursor getListOfAvailabilities(){
        String selectQuery = "SELECT * FROM " + Availability.TABLE;

        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(selectQuery, null);

        //Check if cursor is null and that it is closed prior to return
        if (cursor == null){
            return null;
        } else if (!cursor.moveToFirst()){
            cursor.close();
        }
        return cursor;
    }

    /* Returns true/false based upon the existence of each given author, availability, format and genre */
    public boolean isRelationalDataCorrect(String author, String availability, String format, String genre){
        String ErrorReport = "ERROR!\n";
        Boolean temp = true;

        /* 'Iterate' through each existence-check, if it does not exist append an error message and flip temp bit */

        if (!doesAuthorExist(author)) {
            ErrorReport += "Author Does Not Exist.\n";
            temp = false;
        }

        if (!doesAvailabilityExist(availability)){
            ErrorReport += "Availability Type Does Not Exist.\n";
            temp = false;
        }

        if (!doesFormatExist(format)){
            ErrorReport += "Format Type Does Not Exist.\n";
            temp = false;
        }

        if (!doesGenreExist(genre)){
            ErrorReport += "Genre Does Not Exist.";
            temp = false;
        }

        //If returning error, show error toast before returning
        if (!temp) {
            Toast.makeText(this.context, ErrorReport, Toast.LENGTH_LONG).show();
        }

        return temp;
    }
}
