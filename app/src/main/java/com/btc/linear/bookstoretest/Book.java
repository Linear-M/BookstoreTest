package com.btc.linear.bookstoretest;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Linear on 12/11/2017.
 */

public class Book {
    public static final String TABLE = "Book";

    public static final String KEY_ISBN = "ISBN";
    public static final String KEY_Name = "book_name";
    public static final String KEY_Author = "book_author";
    public static final String KEY_Price = "book_price";
    public static final String KEY_ReleaseDate = "book_release_date";
    public static final String KEY_Format = "book_format";
    public static final String KEY_Review = "book_review";
    public static final String KEY_Availability = "book_availability";
    public static final String KEY_PictureURL = "book_picture_url";
    public static final String KEY_Description = "book_description";

    public long ISBN;
    public String name;
    public String author;
    public double price;
    public String releaseDate;
    public String format;
    public int review;
    public String availability;
    public String pictureUrl;
    public String description;

    public Book(long _ISBN, String Name, String Author, double Price, String ReleaseDate, String Format, int Review, String Availability, String PictureURL, String Description) {
        ISBN = _ISBN;
        name = Name;
        author = Author;
        price = Price;
        releaseDate = ReleaseDate;
        format = Format;
        review = Review;
        availability = Availability;
        pictureUrl = PictureURL;
        description = Description;
    }

    public Book(String Name, String Author, double Price, String ReleaseDate, String Format, int Review, String Availability, String PictureURL, String Description) {
        name = Name;
        author = Author;
        price = Price;
        releaseDate = ReleaseDate;
        format = Format;
        review = Review;
        availability = Availability;
        pictureUrl = PictureURL;
        description = Description;
    }

    @Override
    public String toString() {
        return "ISBN: " + ISBN + "\nName: " + name + "\nAuthor: " + author + "\nPrice: " + price + "\nRelease Date: " + releaseDate + "\nFormat: " + format + "\nReview: " + review
                + "\nAvailability: " + availability + "\nPicture URL: " + pictureUrl;
    }

    public String toDisplayString(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        numberFormat.setCurrency(Currency.getInstance("GBP"));

        return "Name: " + name + "    Price: " + numberFormat.format(price) +  "\n\n" + description;
    }

    public String toModalString(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        numberFormat.setCurrency(Currency.getInstance("GBP"));

        return "ISBN: " + ISBN + "\nName: " + name + "\nAuthor: " + author + "\nPrice: " + numberFormat.format(price)+ "\nRelease Date: " + releaseDate + "\nFormat: " + format
                + "\nAverage Customer Review: " + review + "\nAvailability: " + availability;
    }
}
