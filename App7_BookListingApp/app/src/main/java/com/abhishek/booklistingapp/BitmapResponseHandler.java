package com.abhishek.booklistingapp;


import com.abhishek.booklistingapp.model.HTTPResponse;

public abstract class BitmapResponseHandler  extends ResponseHandler{
    public BitmapResponseHandler()
    {

    }
    public abstract void onFailure(HTTPResponse response);
    public abstract void onError(Throwable error);
    public abstract void onSuccess(HTTPResponse response);
}
