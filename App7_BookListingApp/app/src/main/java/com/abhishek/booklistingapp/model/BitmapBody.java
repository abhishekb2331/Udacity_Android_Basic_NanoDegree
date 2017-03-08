package com.abhishek.booklistingapp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class BitmapBody extends Body{
    private Bitmap bitmap;

    @Override
    public void read(InputStream is) throws IOException
    {
        bitmap = BitmapFactory.decodeStream(is);
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
