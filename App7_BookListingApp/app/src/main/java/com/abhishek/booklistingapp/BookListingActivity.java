package com.abhishek.booklistingapp;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.booklistingapp.adapter.BookAdapter;
import com.abhishek.booklistingapp.model.Book;
import com.abhishek.booklistingapp.model.HTTPRequest;
import com.abhishek.booklistingapp.model.HTTPResponse;

import org.json.JSONException;
import org.parceler.Parcel;
import org.parceler.ParcelFactory;
import org.parceler.Parcels;

import java.util.ArrayList;

public class BookListingActivity extends AppCompatActivity implements BookAdapter.UpdateView{

    private String mQuery=null;
    private EndlessScroller endlessScroller =null;
    private JSONResponseHandler jsonResponseHandler;
    private final String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=";
    private final String startIndexUrl = "&startIndex=";
    private int startIndex = 0;
    private final String maxResultsUrl = "&maxResults=40";
    private ListView listView;
    private ArrayList<Book> books;
    private boolean initLoading =false;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchPlate = (EditText) searchView.findViewById(searchPlateId);

        searchPlate.setHint("Search");

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(BookListingActivity.this, "Query is : " + query, Toast.LENGTH_LONG).show();
                String Query = query.trim().replaceAll("\\s+", "+");
                if(mQuery==null || !mQuery.equalsIgnoreCase(Query)) {
                    mQuery= Query;
                    books.clear();
                    if (listView != null) {
                        BookAdapter bookAdapter = (BookAdapter) listView.getAdapter();
                        if (endlessScroller != null)
                            endlessScroller.resetState();
                        bookAdapter.setServerListSize(-1);
                        bookAdapter.notifyDataSetChanged();
                    }
                    initLoading = false;
                    if(mQuery!=null) {
                        setTitle(mQuery);
                    }
                    makeRequest(0);
                    return true;
                }
                else
                    return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);
        listView = (ListView) findViewById(R.id.list);
        TextView textView = (TextView) findViewById(R.id.text_no_data);
        listView.setEmptyView(textView);
        books = new ArrayList<Book>();
        if(savedInstanceState!=null)
        {
            Parcelable[] parcelables = savedInstanceState.getParcelableArray("SEARCH");
            for (Parcelable i : parcelables)
            {
                books.add((Book) Parcels.unwrap(i));
            }
            mQuery= savedInstanceState.getString("query");
            startIndex=savedInstanceState.getInt("startIndex");
            if(books.size()>0)
                initLoading=true;
            if(mQuery!=null)
                setTitle(mQuery);
        }
        final BookAdapter bookAdapter = new BookAdapter(this, books);
        bookAdapter.setUpdateView(this);
        listView.setAdapter(bookAdapter);
        jsonResponseHandler = new JSONResponseHandler() {
            @Override
            public void onFailure(HTTPResponse response) {
                if(endlessScroller!=null)
                    endlessScroller.setLoading(false);
                bookAdapter.setServerListSize(books.size());
                //bookAdapter.notifyDataSetChanged();
                updateChildView(books.size());
                Toast.makeText(BookListingActivity.this,
                        "Failed to retrieve the content",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable error) {
                if(endlessScroller!=null)
                    endlessScroller.setLoading(false);
                bookAdapter.setServerListSize(books.size());
                //bookAdapter.notifyDataSetChanged();
                updateChildView(books.size());
                Log.e("BookListingActivity", "Exception happened retrieving contents " + error.getMessage(), error);
                Toast.makeText(BookListingActivity.this,
                        "Failed to retrieve the content",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(HTTPResponse response) {
                ArrayList<Book> queryBook = null;
                try {
                    queryBook = JSONBookQuery.read(response.getBody().getContent());
                    if(queryBook!=null)
                        books.addAll(queryBook);
                    else
                        bookAdapter.setServerListSize(books.size());
                    bookAdapter.notifyDataSetChanged();
                    initLoading=true;
                } catch (JSONException e) {
                    Log.e("BookListingActivity", "Exception happened parsing JSON " + e.getMessage(), e);
                    e.printStackTrace();
                }
            }
        };
        //Create an endless scroller with visible threshold as 10 items.
        endlessScroller = new EndlessScroller(10) {
            @Override
            public boolean onLoadMore(int curPageIndex, int totalItemCount) {
                if(mQuery!=null && initLoading==true) {
                    makeRequest(curPageIndex);
                    return true;
                }
                else {
                    return false;
                }
            }
        };
        listView.setOnScrollListener(endlessScroller);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable[] parcelables = new Parcelable[books.size()];
        for(int i=0;i<books.size();i++)
        {
            Book book = books.get(i);
            book.setDrawable(null);
            book.setDrawable_State(Book.DRAWABLE_DEFAULT);
            parcelables[i]= (Parcelable) Parcels.wrap(book);
        }
        outState.putParcelableArray("SEARCH",parcelables);
        outState.putInt("startIndex",startIndex);
        outState.putString("query",mQuery);
    }

    public void makeRequest(int curPageIndex)
    {
        Log.v("MAKE REQUEST "," " + curPageIndex);
        if(mQuery!=null) {
            if (isInternetConnectionAvailable()) {

                startIndex = curPageIndex * 40;
                HTTPRequest.Builder builder = new HTTPRequest.Builder();
                builder.setUrl(baseUrl + mQuery + startIndexUrl + startIndex + maxResultsUrl);
                builder.setVerb(HTTPRequest.VERB_GET);
                builder.setReadTimeOut(10000);
                builder.setConnectTimeOut(15000);
                HTTPRequest httpRequest = builder.build();

                BookListingHTTPTask bookListingHTTPTask = new BookListingHTTPTask(jsonResponseHandler);
                bookListingHTTPTask.execute(httpRequest);
            }
            else
            {
                Toast.makeText(this,"No Internet Connection Available",Toast.LENGTH_SHORT).show();
            }
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
    public void updateChildView(int position) {
        int start = listView.getFirstVisiblePosition();
        int end = listView.getLastVisiblePosition();
        View view;
        if(position>=start && position<=end) {
            view = listView.getChildAt(position-start);
            listView.getAdapter().getView(position,view,listView);
        }
    }
}
