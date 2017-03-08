package com.abhishek.booklistingapp;

import android.os.AsyncTask;
import android.util.Log;

import com.abhishek.booklistingapp.model.BitmapBody;
import com.abhishek.booklistingapp.model.Body;
import com.abhishek.booklistingapp.model.HTTPRequest;
import com.abhishek.booklistingapp.model.HTTPResponse;
import com.abhishek.booklistingapp.model.JSONBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BookListingHTTPTask  extends AsyncTask<HTTPRequest,Void,Result<HTTPResponse>>{
    private ResponseHandler mHandler;
    public BookListingHTTPTask(ResponseHandler handler)
    {
        mHandler=handler;
    }
    @Override
    protected Result<HTTPResponse> doInBackground(HTTPRequest... params) {
        HTTPRequest request = params[0];
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

    public Body readContent(String mimeType, InputStream is) throws IOException {
        Body reader = null;
        if (  mimeType.startsWith("application/json")) {
            reader = new JSONBody();
            reader.read(is);
            return reader;
        }
        else if(mimeType.startsWith("image"))
        {
            reader = new BitmapBody();
            reader.read(is);
            return reader;
        }
        return reader;
    }

    @Override
    protected void onPostExecute(Result<HTTPResponse> httpResponseResult) {
        if(httpResponseResult.error!=null)
        {
            mHandler.onError(httpResponseResult.error);
        }
        else if(httpResponseResult.obj.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            if(httpResponseResult.obj.getBody()  instanceof JSONBody )
                Log.v("HTTP JSON TASK ","called handler success");

            if(httpResponseResult.obj.getBody()  instanceof BitmapBody )
                Log.v("HTTP BITMAP TASK ","called handler success");
            mHandler.onSuccess(httpResponseResult.obj);
        }
        else {
            mHandler.onFailure(httpResponseResult.obj);
        }
    }
}
