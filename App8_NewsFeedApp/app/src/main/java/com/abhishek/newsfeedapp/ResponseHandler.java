package com.abhishek.newsfeedapp;

import com.abhishek.newsfeedapp.model.HTTPResponse;

public abstract class ResponseHandler {
    abstract public void onSuccess(HTTPResponse response) ;

    abstract public void onFailure(HTTPResponse response) ;

    abstract public void onError(Throwable error);
}
