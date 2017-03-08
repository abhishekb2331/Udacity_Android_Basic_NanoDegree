package com.abhishek.newsfeedapp.model;

public class HTTPResponse {
    private int mResponseCode;
    private Body body;

    public HTTPResponse(Builder builder)
    {
        mResponseCode = builder.mResponseCode;
        body=builder.body;
    }

    public Body getBody() {
        return body;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public static class Builder {
        private int mResponseCode;
        private Body body;

        public void setBody(Body body) {
            this.body = body;
        }

        public void setResponseCode(int mResponseCode) {
            this.mResponseCode = mResponseCode;
        }
        public HTTPResponse build()
        {
            return new HTTPResponse(this);
        }
    }
}
