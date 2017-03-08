package com.abhishek.newsfeedapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.abhishek.newsfeedapp.model.Body;
import com.abhishek.newsfeedapp.model.HTTPRequest;
import com.abhishek.newsfeedapp.model.HTTPResponse;
import com.abhishek.newsfeedapp.model.JSONBody;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsFeedHTTPTask extends AsyncTaskLoader<Result<HTTPResponse>>{

    private WeakReference<ResponseHandler> mHandler;
    private WeakReference<HTTPRequest> mRequest;
    public NewsFeedHTTPTask(Context context,ResponseHandler responseHandler,HTTPRequest Request)
    {
        super(context);
        mHandler=new WeakReference<ResponseHandler>(responseHandler);
        mRequest=new WeakReference<HTTPRequest>(Request);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public WeakReference<ResponseHandler> getHandler() {
        return mHandler;
    }

    public void setHandler(ResponseHandler responseHandler)
    {
        mHandler=new WeakReference<ResponseHandler>(responseHandler);
    }

    public void setRequest(HTTPRequest mRequest) {
        this.mRequest=new WeakReference<HTTPRequest>(mRequest);
    }

    public WeakReference<HTTPRequest> getRequest() {
        return mRequest;
    }

    @Override
    public Result<HTTPResponse> loadInBackground() {
        if(mRequest.get()==null) {
          //  Log.v("request ","null");
            return null;
        }
       // Log.v("requesting","true");
        HTTPRequest request = mRequest.get();
        Body body = null;
        HttpURLConnection conn = null;
        Result<HTTPResponse> result = new Result<HTTPResponse>();
        try{
            URL url  = new URL(request.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(request.getReadTimeOut());
            conn.setConnectTimeout(request.getConnectTimeOut());
            conn.setRequestMethod(request.getVerb());
            conn.connect();
            int response_code = conn.getResponseCode();
            HTTPResponse.Builder builder = new HTTPResponse.Builder();
            builder.setResponseCode(response_code);
//            body = new JSONBody();
//          body.read(conn.getInputStream());
            body = readContent(conn.getContentType(),conn.getInputStream());
            builder.setBody(body);
            result.obj = builder.build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result.error=e;
        } catch (IOException e) {
            e.printStackTrace();
            result.error=e;
        }
        finally {
            if(conn!=null)
                conn.disconnect();
        }
        return result;
    }

    private Body readContent(String mimeType, InputStream is) throws IOException {
        Body reader = null;
        if (  mimeType.startsWith("application/json")) {
            reader = new JSONBody();
            reader.read(is);
            return reader;
        }
        return null;
    }
}
