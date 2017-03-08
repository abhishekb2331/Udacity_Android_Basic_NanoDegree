package com.abhishek.booklistingapp.model;

import android.graphics.Bitmap;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class Book {
    public static final int DRAWABLE_DEFAULT =0;
    public static final int DRAWABLE_LOADING =1;
    public static final int DRAWABLE_LOADED =2;
    public static final int DRAWABLE_ILLEGAL=-1;
    private String mTitle;
    private String mAuthor;
    private String ImageUrl;
    private Bitmap Drawable=null;
    private int Drawable_State=0;
    public Book()
    {
    }
    public Book(String Title,String Author)
    {
        mAuthor=Author;
        mTitle=Title;
        ImageUrl=null;
    }

    public Book(String Title,String Author,String ImageURL)
    {
        mAuthor=Author;
        mTitle=Title;
        ImageUrl=ImageURL;
    }
    public void setDrawable(Bitmap drawable) {
        Drawable = drawable;
    }

    public void setDrawable_State(int drawable_State) {
        Drawable_State = drawable_State;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public Bitmap getDrawable() {
        return Drawable;
    }

    public int getDrawable_State() {
        return Drawable_State;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getAuthor() {
        return mAuthor;
    }
    public String getTitle() {
        return mTitle;
    }
    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
