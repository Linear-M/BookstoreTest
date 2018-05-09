package com.btc.linear.bookstoretest.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.btc.linear.bookstoretest.Activities.BookHomeActivity;
import com.btc.linear.bookstoretest.Database.DatabaseHelper;
import com.btc.linear.bookstoretest.Entities.Book;

import java.util.ArrayList;

/**
 * Created by Ben on 13/11/2017.
 */

public class BookCRUD {
    private DatabaseHelper databaseHelper;
    private Context context;

    public BookCRUD(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    /* Using a book object create a ContentValues object to hold a new Book record before using insert */
    public long addBook(Book book) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Book.KEY_ISBN, book.getISBN());
        contentValues.put(Book.KEY_Name, book.getName());
        contentValues.put(Book.KEY_Author, book.getAuthor());
        contentValues.put(Book.KEY_Availability, book.getAvailability());
        contentValues.put(Book.KEY_Format, book.getFormat());
        contentValues.put(Book.KEY_Price, book.getPrice());
        contentValues.put(Book.KEY_ReleaseDate, book.getReleaseDate());
        contentValues.put(Book.KEY_Review, book.getReview());
        contentValues.put(Book.KEY_PictureURL, book.getPictureUrl());
        contentValues.put(Book.KEY_Description, book.getDescription());
        contentValues.put(Book.KEY_Genre, book.getGenre());

        return database.insert(Book.TABLE, null, contentValues);
    }

    /* Delete the book record with the given ISBN */
    public boolean deleteBook(long ISBN){
        return databaseHelper.getWritableDatabase().delete(Book.TABLE, Book.KEY_ISBN + "=" + ISBN, null) > 0;
    }

    /* Update the book cover URL image with the given string for the book with the given ISBN */
    public boolean updateBookCoverURL(String url, long ISBN){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Book.KEY_PictureURL, url);

        return databaseHelper.getWritableDatabase().update(Book.TABLE, contentValues, Book.KEY_ISBN + "=" + ISBN, null) > 0;
    }

    /* Rather than using a Book object, some CPU/disk resources are saved by only passing updatable attributes - use a CV objects to hold this data, and update */
    public boolean updateBook(long ISBN, String bookFormats, String bookAvailability, String bookAuthor, double bookPrice, int customerReview, String releaseDate, String bookName, String bookGenre){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Book.KEY_Format, bookFormats);
        contentValues.put(Book.KEY_Availability, bookAvailability);
        contentValues.put(Book.KEY_Author, bookAuthor);
        contentValues.put(Book.KEY_Price, bookPrice);
        contentValues.put(Book.KEY_Review, customerReview);
        contentValues.put(Book.KEY_ReleaseDate, releaseDate);
        contentValues.put(Book.KEY_Name, bookName);
        contentValues.put(Book.KEY_Genre, bookGenre);

        return databaseHelper.getWritableDatabase().update(Book.TABLE, contentValues, Book.KEY_ISBN + "=" + ISBN, null) > 0;
    }

    /* Use a simple default-modified SQL query to return Book information with names similar to the search criteria */
    public Cursor getListOfSearchedBooks(String search){
        String selectQuery = "SELECT * FROM " + Book.TABLE + " WHERE " + Book.KEY_Name + " LIKE '%" + search + "%'";

        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(selectQuery, null);

        //Ensure a cursor is not null and that it is closed before it is returned
        if (cursor == null){
            return null;
        } else if (!cursor.moveToFirst()){
            cursor.close();
        }
        return cursor;
    }

    /* Returns a full list of every stored book */
    public ArrayList<Book> getListOfBooks() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String getBookSelectQuery = "SELECT "
                + Book.KEY_ISBN + ", "
                + Book.KEY_Name + ", "
                + Book.KEY_Author + ", "
                + Book.KEY_Availability + ", "
                + Book.KEY_Format + ", "
                + Book.KEY_Price + ", "
                + Book.KEY_ReleaseDate + ", "
                + Book.KEY_Review + ", "
                + Book.KEY_Description + ", "
                + Book.KEY_Genre + ", "
                + Book.KEY_PictureURL
                + " FROM " + Book.TABLE;

        //Generate cursor object with returned data
        ArrayList<Book> bookList = new ArrayList<>();
        Cursor cursor = database.rawQuery(getBookSelectQuery, null);

        //For each returned row within the cursor, generate a book object and add it to a temporary book list
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
                String Description = cursor.getString(cursor.getColumnIndex(Book.KEY_Description));
                String Genre = cursor.getString(cursor.getColumnIndex(Book.KEY_Genre));

                bookList.add(new Book(ISBN, Name, Author, Price, ReleaseDate, Format, Review, Availability, PictureURL, Description, Genre));
            } while (cursor.moveToNext());
            cursor.close();
            database.close();
        }
        return bookList;
    }

    /* Use advanced SQL query concatenation to refine the books provided (and their order) based on filters */
    public ArrayList<Book> getFilteredListOfBooks(String advancedFilter, String filter) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String getBookSelectQuery = "SELECT * " + " FROM " + Book.TABLE + " WHERE ";
        boolean isPrice = false;

        //Append table to query based on selected filter
        switch (filter){
            case "Availability":
                getBookSelectQuery += Book.KEY_Availability;
                break;
            case "Format":
                getBookSelectQuery += Book.KEY_Format;
                break;
            case "Genre":
                getBookSelectQuery += Book.KEY_Genre;
                break;
            case "Price":
                //If price is selected, generate new query to retrieve all book data but on ascending or descending order by price
                switch (advancedFilter){
                    case "Low to High":
                        getBookSelectQuery = "SELECT * " + " FROM " + Book.TABLE + " ORDER BY " + Book.KEY_Price + " ASC";
                        isPrice = true;
                        break;
                    case "High to Low":
                        getBookSelectQuery = "SELECT * " + " FROM " + Book.TABLE + " ORDER BY " + Book.KEY_Price + " DESC";
                        isPrice = true;
                        break;
                }
                break;
        }

        //If price query is not selected append advanced filter to query
        if (!isPrice){
            getBookSelectQuery +=  " = '" + advancedFilter + "'";
        }

        ArrayList<Book> bookList = new ArrayList<>();
        Cursor cursor = database.rawQuery(getBookSelectQuery, null);

        //Generate Book objects from cursor iteration and add to temporary list
        if (cursor.moveToFirst()) {
            do {
                long ISBN = cursor.getLong(cursor.getColumnIndex(Book.KEY_ISBN));
                double Price = cursor.getDouble(cursor.getColumnIndex(Book.KEY_Price));
                int Review = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Book.KEY_Review)));
                String Name = cursor.getString(cursor.getColumnIndex(Book.KEY_Name));
                String Author = cursor.getString(cursor.getColumnIndex(Book.KEY_Author));
                String Availability = cursor.getString(cursor.getColumnIndex(Book.KEY_Availability));
                String Format = cursor.getString(cursor.getColumnIndex(Book.KEY_Format));
                String ReleaseDate = cursor.getString(cursor.getColumnIndex(Book.KEY_ReleaseDate));
                String PictureURL = cursor.getString(cursor.getColumnIndex(Book.KEY_PictureURL));
                String Description = cursor.getString(cursor.getColumnIndex(Book.KEY_Description));
                String Genre = cursor.getString(cursor.getColumnIndex(Book.KEY_Genre));

                bookList.add(new Book(ISBN, Name, Author, Price, ReleaseDate, Format, Review, Availability, PictureURL, Description, Genre));
            } while (cursor.moveToNext());
            cursor.close();
            database.close();
        }
        return bookList;
    }

    /* Search for and instantiate a Book object based on the data returned from a WHERE SQL search based on given ISBN */
    public Book getBookByISBN(long _ISBN){
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        //Select all book information where ISBN matches, iterate through cursor row and return a new book object
        String getBookSelectQuery = "SELECT * FROM " + Book.TABLE + " WHERE " + Book.KEY_ISBN + " = " + _ISBN;

        Cursor cursor = database.rawQuery(getBookSelectQuery, null);

        if (cursor.moveToNext()) {
            long ISBN = cursor.getLong(cursor.getColumnIndex(Book.KEY_ISBN));
            String Name = cursor.getString(cursor.getColumnIndex(Book.KEY_Name));
            String Author = cursor.getString(cursor.getColumnIndex(Book.KEY_Author));
            String Availability = cursor.getString(cursor.getColumnIndex(Book.KEY_Availability));
            String Format = cursor.getString(cursor.getColumnIndex(Book.KEY_Format));
            double Price = cursor.getDouble(cursor.getColumnIndex(Book.KEY_Price));
            String ReleaseDate = cursor.getString(cursor.getColumnIndex(Book.KEY_ReleaseDate));
            int Review = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Book.KEY_Review)));
            String PictureURL = cursor.getString(cursor.getColumnIndex(Book.KEY_PictureURL));
            String Description = cursor.getString(cursor.getColumnIndex(Book.KEY_Description));
            String Genre = cursor.getString(cursor.getColumnIndex(Book.KEY_Genre));

            return new Book(ISBN, Name, Author, Price, ReleaseDate, Format, Review, Availability, PictureURL, Description, Genre);
        }
        return null;
    }
}
