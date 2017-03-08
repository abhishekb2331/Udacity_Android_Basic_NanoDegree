package com.abhishek.newsfeedapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abhishek.newsfeedapp.R;
import com.abhishek.newsfeedapp.model.NewsFeed;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<NewsFeed> {
    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;
    private int serverListSize=-1;
    private UpdateView mUpdateView;

    public NewsAdapter(Context context, ArrayList<NewsFeed> newsFeeds)
    {
        super(context,0,newsFeeds);
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
    public NewsFeed getItem(int position) {
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

        NewsFeed newsFeed = getItem(position);
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
        viewHolder.getTitle().setText(newsFeed.getTitle());
        viewHolder.getAuthor().setText(newsFeed.getAuthor());
        viewHolder.getDate().setText(newsFeed.getDate());
        viewHolder.getSection().setText(newsFeed.getSection());
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
        TextView Date;
        TextView Section;
        public ViewHolder(View view)
        {
            Title = (TextView) view.findViewById(R.id.title);
            Author = (TextView) view.findViewById(R.id.author);
            Date = (TextView) view.findViewById(R.id.date);
            Section = (TextView) view.findViewById(R.id.section);
        }

        public TextView getAuthor() {
            return Author;
        }
        public TextView getTitle() {
            return Title;
        }
        public TextView getDate() {return Date;}
        public TextView getSection() {
            return Section;
        }

        public void setAuthor(TextView author) {
            Author = author;
        }
        public void setTitle(TextView title) {
            Title = title;
        }
        public void setDate(TextView date) {Date=date;}
        public void setSection(TextView section) {
            Section = section;
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
