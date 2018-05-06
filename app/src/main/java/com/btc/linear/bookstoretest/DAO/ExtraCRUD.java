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
public class ExtraCRUD {
    private DatabaseHelper databaseHelper;
    private Context context;

    public ExtraCRUD(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }


    public long addGenre(Genre genre) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Genre.KEY_GENRE_TYPE, genre.genreType);
        contentValues.put(Genre.KEY_GENRE_DESCRIPTION, genre.genreDescription);

        return database.insert(Genre.TABLE, null, contentValues);
    }

    public long addFormat(Format format) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Format.KEY_FORMAT_TYPE, format.formatType);

        return database.insert(Format.TABLE, null, contentValues);
    }

    public long addAvailability(Availability availability) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Availability.KEY_AVAILABILITY_TYPE, availability.availabilityType);

        return database.insert(Availability.TABLE, null, contentValues);
    }

    public long addAuthor(Author author) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Author.KEY_AUTHOR_FULL_NAME, author.authorFullName);

        return database.insert(Author.TABLE, null, contentValues);
    }

    /* Does the genre string exist within the genre table - i.e, is it valid? */
    public boolean doesGenreExist(String genre) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT * FROM " + Genre.TABLE + " WHERE " + Genre.KEY_GENRE_TYPE + "=?";

        return database.rawQuery(getUserPasswordQuery, new String[]{genre}).moveToFirst() ? true : false;
    }

    /* Does the format string exist within the genre table - i.e, is it valid? */
    public boolean doesFormatExist(String format) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT * FROM " + Format.TABLE + " WHERE " + Format.KEY_FORMAT_TYPE + "=?";

        return database.rawQuery(getUserPasswordQuery, new String[]{format}).moveToFirst() ? true : false;
    }

    /* Does the availability string exist within the genre table - i.e, is it valid? */
    public boolean doesAvailabilityExist(String availability) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT * FROM " + Availability.TABLE + " WHERE " + Availability.KEY_AVAILABILITY_TYPE + "=?";

        return database.rawQuery(getUserPasswordQuery, new String[]{availability}).moveToFirst() ? true : false;
    }

    /* Does the genre string exist within the genre table - i.e, is it valid? */
    public boolean doesAuthorExist(String authorFullName) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT * FROM " + Author.TABLE + " WHERE " + Author.KEY_AUTHOR_FULL_NAME + "=?";

        return database.rawQuery(getUserPasswordQuery, new String[]{authorFullName}).moveToFirst() ? true : false;
    }

    /* Use a simple default-modified SQL query to return genre information */
    public Cursor getListOfGenres(){
        String selectQuery = "SELECT * FROM " + Genre.TABLE;

        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(selectQuery, null);

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

        if (cursor == null){
            return null;
        } else if (!cursor.moveToFirst()){
            cursor.close();
        }
        return cursor;
    }

    public boolean isRelationalDataCorrect(String author, String availability, String format, String genre){
        String ErrorReport = "ERROR!\n";
        Boolean temp = true;

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

        if (!temp) {
            Toast.makeText(this.context, ErrorReport, Toast.LENGTH_LONG).show();
        }

        return temp;
    }
}
