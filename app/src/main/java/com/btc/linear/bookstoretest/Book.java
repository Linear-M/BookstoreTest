package com.btc.linear.bookstoretest;

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

    public int ISBN;
    public String name;
    public String author;
    public double price;
    public String releaseDate;
    public String format;
    public int review;
    public String availability;

    public Book(int _ISBN, String Name, String Author, double Price, String ReleaseDate, String Format, int Review, String Availability) {
        ISBN = _ISBN;
        name = Name;
        author = Author;
        price = Price;
        releaseDate = ReleaseDate;
        format = Format;
        review = Review;
        availability = Availability;
    }

    public Book(String Name, String Author, double Price, String ReleaseDate, String Format, int Review, String Availability) {
        name = Name;
        author = Author;
        price = Price;
        releaseDate = ReleaseDate;
        format = Format;
        review = Review;
        availability = Availability;
    }

    @Override
    public String toString() {
        return "ISBN: " + ISBN + "\nName: " + name + "\nAuthor: " + author + "\nPrice: " + price + "\nRelease Date: " + releaseDate + "\nFormat: " + format + "\nReview: " + review
                + "\nAvailability: " + availability;
    }
}
