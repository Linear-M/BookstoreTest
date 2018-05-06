package com.btc.linear.bookstoretest.Entities;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Ben on 12/11/2017.
 * This is the Book entity class - final parameters are used so that Book Keys can be called without instantiation, and ensures data integrity.
 *
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

    //region Getters and Setters
    public static String getKEY_Genre() {
        return KEY_Genre;
    }
    public static final String KEY_Genre = "book_genre";

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //endregion

    //Attributes
    public long ISBN;
    public double price;
    public int review;
    public String name;
    public String author;
    public String releaseDate;
    public String format;

    public String availability;
    public String pictureUrl;
    public String description;
    public String genre;

    public Book(long _ISBN, String Name, String Author, double Price, String ReleaseDate, String Format, int Review, String Availability, String PictureURL, String Description, String Genre) {
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
        genre = Genre;
    }

    /* Generic toString */
    @Override
    public String toString() {
        return "ISBN: " + ISBN + "\nName: " + name + "\nAuthor: " + author + "\nPrice: " + price + "\nRelease Date: " + releaseDate + "\nFormat: " + format + "\nReview: " + review
                + "\nAvailability: " + availability + "\nPicture URL: " + pictureUrl;
    }

    /* Simple toString with minimalist use */
    public String toDisplayString(){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        numberFormat.setCurrency(Currency.getInstance("GBP"));

        return "Name: " + name + "    Price: " + numberFormat.format(price) +  "\n\n" + description;
    }

    /* toString used to display full book information in dialog on book click */
    public String toModalString(){
        //Format price from double to relevant currency string (QoL)
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        numberFormat.setCurrency(Currency.getInstance("GBP"));

        return "ISBN: " + ISBN + "\nName: " + name + "\nAuthor: " + author + "\nPrice: " + numberFormat.format(price)+ "\nRelease Date: " + releaseDate + "\nFormat: " + format
                + "\nAverage Customer Review: " + review + "\nAvailability: " + availability;

    }
}
