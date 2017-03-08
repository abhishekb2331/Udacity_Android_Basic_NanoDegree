package com.abhishek.booklistingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abhishek.booklistingapp.BitmapResponseHandler;
import com.abhishek.booklistingapp.BookListingHTTPTask;
import com.abhishek.booklistingapp.R;
import com.abhishek.booklistingapp.model.Book;
import com.abhishek.booklistingapp.model.HTTPRequest;
import com.abhishek.booklistingapp.model.HTTPResponse;

import java.util.ArrayList;


public class BookAdapter extends ArrayAdapter<Book> {
    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;
    private int serverListSize=-1;
    private UpdateView mUpdateView;
    public BookAdapter(Context context, ArrayList<Book> books)
    {
        super(context,0,books);
    }


    public void setServerListSize(int serverListSize){
        this.serverListSize = serverListSize;
    }

    @Override
    public boolean isEnabled(int position) {

        return getItemViewType(position) == VIEW_TYPE_ACTIVITY;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return (super.getCount() > 0) ? (super.getCount() + 1) : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= super.getCount()) ? VIEW_TYPE_LOADING
                : VIEW_TYPE_ACTIVITY;
    }

    @Override
    public Book getItem(int position) {
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ?
                super.getItem(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? position
                : -1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == VIEW_TYPE_LOADING) {
            return getFooterView(position, convertView, parent);
        }

        Book book = getItem(position);
        if(book.getDrawable_State()==Book.DRAWABLE_DEFAULT)
        {
            loadBitmap(book);
        }
        View view=convertView;
        ViewHolder viewHolder;
        if(convertView==null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
            viewHolder = new ViewHolder(view);
        }
        else
        {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.getTitle().setText(book.getTitle());
        viewHolder.getAuthor().setText(book.getAuthor());
       // viewHolder.getImage().setImageResource(R.mipmap.search);
        if(book.getDrawable_State()==Book.DRAWABLE_LOADED)
        {
            viewHolder.getImage().setImageBitmap(book.getDrawable());
        }
        view.setTag(viewHolder);
        return view;
    }

    public View getFooterView(int position, View convertView,
                              ViewGroup parent) {
        if (position >= serverListSize && serverListSize > 0) {
            // the ListView has reached the last row
            TextView tvLastRow = new TextView(getContext());
            tvLastRow.setHint("Reached the last row.");
            tvLastRow.setGravity(Gravity.CENTER);
            return tvLastRow;
        }

        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(getContext()).inflate(
                    R.layout.progress, parent, false);
            ProgressBar progressBar = (ProgressBar) row.findViewById(R.id.progressBar);
            progressBar.setIndeterminate(true);
        }
        return row;
    }

    public static class ViewHolder
    {
        TextView Title;
        TextView Author;
        ImageView Image;
        public ViewHolder(View view)
        {
            Title = (TextView) view.findViewById(R.id.title);
            Author = (TextView) view.findViewById(R.id.author);
            Image = (ImageView) view.findViewById(R.id.image);
        }

        public TextView getAuthor() {
            return Author;
        }
        public TextView getTitle() {
            return Title;
        }
        public ImageView getImage() {return Image;}

        public void setAuthor(TextView author) {
            Author = author;
        }
        public void setTitle(TextView title) {
            Title = title;
        }
        public void setImage(ImageView image) {Image = image;}
    }

    public void loadBitmap(Book book)
    {
        if(book.getImageUrl()!=null) {
            BookBitmapResponseHandler bookBitmapResponseHandler = new BookBitmapResponseHandler(book);
            HTTPRequest.Builder builder = new HTTPRequest.Builder();
            builder.setUrl(book.getImageUrl());
            builder.setVerb(HTTPRequest.VERB_GET);
            builder.setReadTimeOut(10000);
            builder.setConnectTimeOut(15000);
            HTTPRequest httpRequest = builder.build();

            BookListingHTTPTask bookListingHTTPTask = new BookListingHTTPTask(bookBitmapResponseHandler);
            bookListingHTTPTask.execute(httpRequest);

            book.setDrawable_State(Book.DRAWABLE_LOADING);
        }
        else
            book.setDrawable_State(Book.DRAWABLE_ILLEGAL);
    }
    public class BookBitmapResponseHandler extends BitmapResponseHandler
    {
        private Book mBook;
        public BookBitmapResponseHandler(Book book)
        {
            mBook=book;
        }
        @Override
        public void onFailure(HTTPResponse response) {

            Log.e("BookAdapter", "Failed to retrieve the images");
        }

        @Override
        public void onError(Throwable error) {

            Log.e("BookAdapter", "Exception happened retrieving images " + error.getMessage(), error);
        }

        @Override
        public void onSuccess(HTTPResponse response) {
            mBook.setDrawable(response.getBody().getBitmap());
            mBook.setDrawable_State(Book.DRAWABLE_LOADED);
          //  Log.v("BookAdapter", "Retrieved Bitmap" + getPosition(mBook));
            if(mUpdateView!=null)
                mUpdateView.updateChildView(getPosition(mBook));
        }
    }
    public void setUpdateView(UpdateView updateView)
    {
        mUpdateView=updateView;
    }
    public static interface UpdateView{
        public abstract void updateChildView(int position);
    }
}
