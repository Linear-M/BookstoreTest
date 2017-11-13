package com.btc.linear.bookstoretest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Linear on 13/11/2017.
 */

public class BookCRUD {
    private DatabaseHelper databaseHelper;

    public BookCRUD(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long addBook(Book book) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Book.KEY_ISBN, book.ISBN);
        contentValues.put(Book.KEY_Name, book.name);
        contentValues.put(Book.KEY_Author, book.author);
        contentValues.put(Book.KEY_Availability, book.availability);
        contentValues.put(Book.KEY_Format, book.format);
        contentValues.put(Book.KEY_Price, book.price);
        contentValues.put(Book.KEY_ReleaseDate, book.releaseDate);
        contentValues.put(Book.KEY_Review, book.review);
        contentValues.put(Book.KEY_PictureURL, book.pictureUrl);

        return database.insert(Book.TABLE, null, contentValues);
    }

    public ArrayList<Book> getListOfBooks() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //Verbose query again tbh
        String getBookSelectQuery = "SELECT "
                + Book.KEY_ISBN + ", "
                + Book.KEY_Name + ", "
                + Book.KEY_Author + ", "
                + Book.KEY_Availability + ", "
                + Book.KEY_Format + ", "
                + Book.KEY_Price + ", "
                + Book.KEY_ReleaseDate + ", "
                + Book.KEY_Review + ", "
                + Book.KEY_PictureURL
                + " FROM " + Book.TABLE;

        ArrayList<Book> bookList = new ArrayList<>();
        Cursor cursor = database.rawQuery(getBookSelectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                long ISBN = cursor.getLong(cursor.getColumnIndex(Book.KEY_ISBN));
                String Name = cursor.getString(cursor.getColumnIndex(Book.KEY_Name));
                String Author = cursor.getString(cursor.getColumnIndex(Book.KEY_Author));
                String Availability = cursor.getString(cursor.getColumnIndex(Book.KEY_Availability));
                String Format = cursor.getString(cursor.getColumnIndex(Book.KEY_Format));
                double Price = cursor.getDouble(cursor.getColumnIndex(Book.KEY_Price));
                String ReleaseDate = cursor.getString(cursor.getColumnIndex(Book.KEY_ReleaseDate));
                int Review = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Book.KEY_Review)));
                String PictureURL = cursor.getString(cursor.getColumnIndex(Book.KEY_PictureURL));
                bookList.add(new Book(ISBN, Name, Author, Price, ReleaseDate, Format, Review, Availability, PictureURL));
            } while (cursor.moveToNext());

            cursor.close();
            database.close();
        }
        return bookList;
    }
}
