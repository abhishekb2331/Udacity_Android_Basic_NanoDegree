package com.abhishek.newsfeedapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.abhishek.newsfeedapp.adapter.NewsAdapter;
import com.abhishek.newsfeedapp.model.HTTPRequest;
import com.abhishek.newsfeedapp.model.HTTPResponse;
import com.abhishek.newsfeedapp.model.JSONBody;
import com.abhishek.newsfeedapp.model.NewsFeed;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.ArrayList;

public class NewsFeedActivity extends AppCompatActivity implements NewsAdapter.UpdateView,LoaderManager.LoaderCallbacks<Result<HTTPResponse>>,SwipeRefreshLayout.OnRefreshListener{
    private ListView listView;
    private JSONResponseHandler jsonResponseHandler;
    private HTTPRequest request;
    private static final int LOADER_ID=0;
    private static final int LOADER_ID_MORE=1;
    private int serverListSize=-1;
    private EndlessScroller endlessScroller=null;
    private int curPage=1;
    private NewsAdapter newsAdapter=null;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_acrivity);
        listView = (ListView) findViewById(R.id.list);
        TextView textView = (TextView) findViewById(R.id.text_no_data);
        listView.setEmptyView(textView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        ArrayList<NewsFeed> newsFeeds = new ArrayList<>();

        newsAdapter = new NewsAdapter(this,newsFeeds);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Url = newsAdapter.getItem(position).getURL();
                if(Url!=null) {
                    Uri uri= Uri.parse(Url);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });
//        Log.v("URL : ",JSONNewsQuery.createQueryURL(1));

        jsonResponseHandler = new NewsJSONResponseHandler();
        //Create an endless scroller with visible threshold as 2 item.
        endlessScroller= new EndlessScroller(2) {
            @Override
            public boolean onLoadMore(int curPageIndex, int totalItemCount) {
                if(curPage==curPageIndex) {
                    makeRequest(curPageIndex);
                    getSupportLoaderManager().restartLoader(LOADER_ID_MORE, null, NewsFeedActivity.this);
                    return true;
                }
                return false;
            }
        };
        listView.setOnScrollListener(endlessScroller);
        makeRequest(1);
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    public class NewsJSONResponseHandler extends JSONResponseHandler
    {

        @Override
        public void onFailure(HTTPResponse response) {
            Log.e("NewsFeed", "Failed to retrieve the content");
        }

        @Override
        public void onError(Throwable error) {
            Log.e("NewsFeed", "Exception happened retrieving contents " + error.getMessage(), error);
        }

        @Override
        public void onSuccess(HTTPResponse response) {

            try {
                serverListSize=JSONNewsQuery.getServerResultLength(response.getBody().getContent());
                newsAdapter.setServerListSize(serverListSize);
                newsAdapter.addAll(JSONNewsQuery.read(response.getBody().getContent()));
                curPage++;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
      //      Log.v("NewsFeed loading"," Contents Loaded ");
        }
    }
    @Override
    public void updateChildView(int position) {
        int start = listView.getFirstVisiblePosition();
        int end = listView.getLastVisiblePosition();
        View view;
        if(position>=start && position<=end) {
            view = listView.getChildAt(position-start);
            listView.getAdapter().getView(position,view,listView);
        }
    }

    @Override
    protected void onDestroy() {
        listView=null;
        jsonResponseHandler=null;
        request=null;
        endlessScroller=null;
        newsAdapter.clear();
        newsAdapter=null;
        swipeRefreshLayout=null;

        if(getSupportLoaderManager().getLoader(LOADER_ID)!=null)
        {
            Loader<Result<HTTPResponse>> loader =getSupportLoaderManager().getLoader(LOADER_ID);
            ((NewsFeedHTTPTask)loader).setHandler(null);
            ((NewsFeedHTTPTask)loader).setRequest(null);
        }
        if(getSupportLoaderManager().getLoader(LOADER_ID_MORE)!=null)
        {
            Loader<Result<HTTPResponse>> loader =getSupportLoaderManager().getLoader(LOADER_ID_MORE);
            ((NewsFeedHTTPTask)loader).setHandler(null);
            ((NewsFeedHTTPTask)loader).setRequest(null);
        }
        super.onDestroy();
    }

    @Override
    public Loader<Result<HTTPResponse>> onCreateLoader(int id, Bundle args) {
        if(request!=null) {
            return new NewsFeedHTTPTask(this, jsonResponseHandler, request);
        }
        else
            return null;
    }

    @Override
    public void onLoadFinished(Loader<Result<HTTPResponse>> loader, Result<HTTPResponse> httpResponseResult) {
        swipeRefreshLayout.setRefreshing(false);
        if(loader == null)
            return;
        WeakReference<ResponseHandler> mWeakHandler = ((NewsFeedHTTPTask) loader).getHandler();
        if(mWeakHandler.get()!=null) {
            ResponseHandler mHandler = mWeakHandler.get();
            if (httpResponseResult.error != null) {
                mHandler.onError(httpResponseResult.error);
            } else if (httpResponseResult.obj.getResponseCode() == HttpURLConnection.HTTP_OK) {
                mHandler.onSuccess(httpResponseResult.obj);
            } else {
                mHandler.onFailure(httpResponseResult.obj);
            }
        }
        else
        {
            if(httpResponseResult.obj.getBody() instanceof JSONBody)
            {
                ((NewsFeedHTTPTask) loader).setHandler(jsonResponseHandler);
                makeRequest(1);
                ((NewsFeedHTTPTask) loader).setRequest(request);
                ResponseHandler mHandler = jsonResponseHandler;
                if (httpResponseResult.error != null) {
                    mHandler.onError(httpResponseResult.error);
                } else if (httpResponseResult.obj.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    mHandler.onSuccess(httpResponseResult.obj);
                } else {
                    mHandler.onFailure(httpResponseResult.obj);
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Result<HTTPResponse>> loader) {

    }

    public void makeRequest(int curPageIndex) {
        if (isInternetConnectionAvailable()) {
            HTTPRequest.Builder builder = new HTTPRequest.Builder();
            builder.setUrl(JSONNewsQuery.createQueryURL(curPageIndex));
            builder.setVerb(HTTPRequest.VERB_GET);
            builder.setReadTimeOut(10000);
            builder.setConnectTimeOut(15000);
            request = builder.build();
        }
    }

    public boolean isInternetConnectionAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null)
            return activeNetwork.isConnectedOrConnecting();
        else
            return false;
    }

    @Override
    public void onRefresh() {
  //      Log.v("Refresh","true");
        newsAdapter.clear();
        curPage=1;
        makeRequest(1);
        getSupportLoaderManager().restartLoader(LOADER_ID,null,this);
    }
}
