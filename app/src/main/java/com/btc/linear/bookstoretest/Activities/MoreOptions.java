package com.btc.linear.bookstoretest.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.btc.linear.bookstoretest.DAO.BookCRUD;
import com.btc.linear.bookstoretest.DAO.ExtraCRUD;
import com.btc.linear.bookstoretest.Entities.Author;
import com.btc.linear.bookstoretest.Entities.Availability;
import com.btc.linear.bookstoretest.Entities.Book;
import com.btc.linear.bookstoretest.Entities.Format;
import com.btc.linear.bookstoretest.Entities.Genre;
import com.btc.linear.bookstoretest.R;

import java.util.Random;

public class MoreOptions extends Activity implements View.OnClickListener {

    TextView newGenre, newAuthor, newAvailability, newFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_options);

        this.newGenre = findViewById(R.id.txtAddNewGenre);
        this.newFormat = findViewById(R.id.txtAddNewFormat);
        this.newAuthor = findViewById(R.id.txtAddNewAuthor);
        this.newAvailability = findViewById(R.id.txtAddNewAvailability);
        this.setTitle("Bookstore - More ACP Options");
    }

    public void onClick(View view){
        if (view == findViewById(R.id.btnAddNewGenre)){
            addGenre();
        } else if (view == findViewById(R.id.btnAddNewAuthor)){
            addAuthor();
        } else if (view == findViewById(R.id.btnAddNewFormat)){
            addFormat();
        } else if (view == findViewById(R.id.btnAddNewAvailability)){
            addAvailability();
        }
    }

    /* Fetch information from widgets, instantiate new genre and use the DAO to amend database */
    private void addGenre(){
        ExtraCRUD extraCRUD = new ExtraCRUD(this);
        String genreType = newGenre.getText().toString();

        Toast.makeText(this, String.valueOf(extraCRUD.addGenre(new Genre(genreType, "")) + " - " + genreType), Toast.LENGTH_LONG).show();
    }

    /* Fetch information from widgets, instantiate new author and use the DAO to amend database */
    private void addAuthor(){
        ExtraCRUD extraCRUD = new ExtraCRUD(this);
        String authorName = newAuthor.getText().toString();

        Toast.makeText(this, String.valueOf(extraCRUD.addAuthor(new Author(authorName)) + " - " + authorName), Toast.LENGTH_LONG).show();
    }

    /* Fetch information from widgets, instantiate new format and use the DAO to amend database */
    private void addFormat(){
        ExtraCRUD extraCRUD = new ExtraCRUD(this);
        String formatType = newFormat.getText().toString();

        Toast.makeText(this, String.valueOf(extraCRUD.addFormat(new Format(formatType)) + " - " + formatType), Toast.LENGTH_LONG).show();
    }

    /* Fetch information from widgets, instantiate new availability and use the DAO to amend database */
    private void addAvailability(){
        ExtraCRUD extraCRUD = new ExtraCRUD(this);
        String availabilityType = newAvailability.getText().toString();

        Toast.makeText(this, String.valueOf(extraCRUD.addAvailability(new Availability(availabilityType)) + " - " + availabilityType), Toast.LENGTH_LONG).show();
    }
}
