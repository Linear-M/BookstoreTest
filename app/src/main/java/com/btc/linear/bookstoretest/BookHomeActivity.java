package com.btc.linear.bookstoretest;

import android.app.Activity;
import android.os.Bundle;

public class BookHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_home);
        this.setTitle(User.loggedInUser.username + "'s Bookstore");
    }


}
