package com.btc.linear.bookstoretest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class BookHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_home);
        this.setTitle(User.loggedInUser.username + "'s Bookstore");

        BookCRUD bookCRUD = new BookCRUD(this);
        ArrayList<Book> bookList = bookCRUD.getListOfBooks();
        if (bookList.size() > 0) {
            for (Book book : bookList) {
                Toast.makeText(this, book.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


}
