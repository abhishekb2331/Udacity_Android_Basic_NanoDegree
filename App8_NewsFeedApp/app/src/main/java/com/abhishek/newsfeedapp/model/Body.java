package com.abhishek.newsfeedapp.model;

import java.io.IOException;
import java.io.InputStream;

public abstract class Body {
    public abstract void read(InputStream is) throws IOException;
    public abstract String getContent();
}