package com.example.android.bookapp;

import android.graphics.Bitmap;

/**
 * Created by Noemi on 12/21/2017.
 */

public class Book {

    private String mAuthor;

    private String mTitle;

    private String mDescription;

    private Bitmap mImage;

    private double mPrice;

    private String mUrl;

    public Book ( String author, String title, String description, Bitmap image, double price, String url){
        mAuthor = author;
        mImage = image;
        mPrice = price;
        mDescription = description;
        mTitle = title;
        mUrl = url;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public String getTitle (){
        return mTitle;
    }

    public String getDescription(){
        return mDescription;
    }

    public Bitmap getImage(){
        return mImage;
    }

    public double getPrice(){
        return mPrice;
    }

    public String getUrl(){
        return mUrl;
    }



}
