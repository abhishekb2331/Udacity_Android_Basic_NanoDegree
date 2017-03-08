package com.abhishek.newsfeedapp;

import com.abhishek.newsfeedapp.model.HTTPResponse;

public abstract class JSONResponseHandler extends ResponseHandler{
    public JSONResponseHandler()
    {

    }
    public abstract void onFailure(HTTPResponse response);
    public abstract void onError(Throwable error);
    public abstract void onSuccess(HTTPResponse response);
}