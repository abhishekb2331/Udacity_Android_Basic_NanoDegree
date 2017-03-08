package com.abhishek.booklistingapp.model;

import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class JSONBody extends Body{

    private String mContent;
    public  JSONBody()
    {
    }

    @Override
    public String getContent() {
        return mContent;
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public void read(InputStream is) throws IOException {
        StringBuilder output = new StringBuilder();
        if(is!=null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String newLine= bufferedReader.readLine();
            while (newLine!=null)
            {
                output.append(newLine);
                newLine= bufferedReader.readLine();
            }
        }
        mContent=output.toString();
    }
}
