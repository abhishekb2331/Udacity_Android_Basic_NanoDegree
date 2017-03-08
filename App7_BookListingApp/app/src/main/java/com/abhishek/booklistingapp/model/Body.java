package com.abhishek.booklistingapp.model;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.InputStream;

public abstract class Body {
    public abstract void read(InputStream is) throws IOException;
    public abstract String getContent();
    public abstract Bitmap getBitmap();
}
