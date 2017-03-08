package com.abhishek.booklistingapp;

import com.abhishek.booklistingapp.model.HTTPResponse;

public abstract class ResponseHandler {
    abstract public void onSuccess(HTTPResponse response) ;

    abstract public void onFailure(HTTPResponse response) ;

    abstract public void onError(Throwable error);
}

