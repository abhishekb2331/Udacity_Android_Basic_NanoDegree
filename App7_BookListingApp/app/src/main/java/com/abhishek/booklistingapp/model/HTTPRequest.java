package com.abhishek.booklistingapp.model;


public class HTTPRequest {
    public static final String VERB_GET="GET";
    private String mVerb;
    private String mUrl;
    private int mReadTimeOut;
    private int mConnectTimeOut;

    public HTTPRequest(Builder builder)
    {
        mVerb=builder.mVerb;
        mUrl=builder.mUrl;
        mConnectTimeOut=builder.mConnectTimeOut;
        mReadTimeOut=builder.mReadTimeOut;
    }

    public int getConnectTimeOut() {
        return mConnectTimeOut;
    }

    public int getReadTimeOut() {
        return mReadTimeOut;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getVerb() {
        return mVerb;
    }

    public static class Builder{
        private String mVerb;
        private String mUrl;
        private int mReadTimeOut;
        private int mConnectTimeOut;

        public void setUrl(String mUrl) {
            this.mUrl = mUrl;
        }

        public void setVerb(String mVerb) {
            this.mVerb = mVerb;
        }
        public void setReadTimeOut(int mReadTimeOut) {
            this.mReadTimeOut = mReadTimeOut;
        }
        public void setConnectTimeOut(int mConnectTimeOut) {
            this.mConnectTimeOut = mConnectTimeOut;
        }

        public HTTPRequest build()
        {
            return new HTTPRequest(this);
        }
    }
}
