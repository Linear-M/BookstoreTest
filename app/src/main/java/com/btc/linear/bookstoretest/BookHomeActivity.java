package com.btc.linear.bookstoretest;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_home);
        final View linearLayout =  findViewById(R.id.lytInfo);
        this.setTitle(User.loggedInUser.username + "'s Bookstore");


        BookCRUD bookCRUD = new BookCRUD(this);
        ArrayList<Book> bookList = bookCRUD.getListOfBooks();
        if (bookList.size() > 0) {
            for (Book book : bookList) {
                final Book tempBook = book;
                LinearLayout sideBySide = new LinearLayout(this);
                sideBySide.setOrientation(LinearLayout.HORIZONTAL);

                TextView bookView = new TextView(this);
                bookView.setText(book.toDisplayString());
                bookView.setId((int)book.ISBN);
                bookView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                ImageView bookCover = new ImageView(this);
                Picasso.with(getApplicationContext()).load(Uri.parse(book.pictureUrl)).into(bookCover);
                LinearLayout.LayoutParams coverParams = new LinearLayout.LayoutParams(
                        270,
                        480
                );
                coverParams.setMargins(50,-10,64,0);
                bookCover.setLayoutParams(coverParams);

                View seperator = new View(this);
                LinearLayout.LayoutParams bookInfoParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2
                );
                bookInfoParams.setMargins(0,16,0,16);
                seperator.setLayoutParams(bookInfoParams);
                seperator.setBackgroundColor(Color.LTGRAY);

                bookCover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!isFinishing()){
                                    new AlertDialog.Builder(BookHomeActivity.this)
                                            .setTitle(tempBook.name + " - " + tempBook.author)
                                            .setMessage(tempBook.toModalString())
                                            .setCancelable(false)
                                            .setPositiveButton("Ya Done Now", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            }).show();
                                }
                            }
                        });
                    }
                });

                sideBySide.addView(bookCover);
                sideBySide.addView(bookView);

                ((LinearLayout)linearLayout).addView(sideBySide);
                ((LinearLayout)linearLayout).addView(seperator);
                }
            }
        }
    }
