package com.example.android.bookapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    private static String tempLink;

    private static final int BOOK_LOADER_ID = 1;

    private BookAdapter mAdapter;

    private TextView mEmptyTextView;

    private EditText searchText;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        searchText = findViewById(R.id.edit_text);
        searchText.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_IN);


        ListView listView = findViewById(R.id.list_view);
        mEmptyTextView = findViewById(R.id.welcome_text);
        listView.setEmptyView(mEmptyTextView);

        mProgressBar = findViewById(R.id.progress_indicator);
        ImageView searchButton = findViewById(R.id.search);


        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        listView.setAdapter(mAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.clear();
                String searchBooks = searchText.getText().toString();
                tempLink = "https://www.googleapis.com/books/v1/volumes?q=";
                tempLink= tempLink + searchBooks;
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(searchBooks!=null && !searchBooks.isEmpty()&& networkInfo.isConnected()){;
                    mEmptyTextView.setVisibility(view.GONE);
                    mProgressBar.setVisibility(view.VISIBLE);
                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);}


            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Book books = mAdapter.getItem(position);
                Uri booksUri = Uri.parse(books.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, booksUri);
                startActivity(intent);

            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        }
        else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText("No internet connection");
        }


    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, tempLink);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        mProgressBar.setVisibility(View.GONE);

        mAdapter.clear();
        if (books!=null && !books.isEmpty()){
            mAdapter.addAll(books);
        }
        else {
            mEmptyTextView.setText("No books found");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }


}
