package com.abhishek.musicstructureapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SongAdapter extends ArrayAdapter<String>{
    public SongAdapter(Context context, List<String> songs)
    {
      super(context,0,songs);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if(view==null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.album_listview_item,parent,false);
            viewHolder = new ViewHolder(view);
        }
        else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.songId.setText(""+(position+1));
        viewHolder.songName.setText(getItem(position));
        view.setTag(viewHolder);
        return view;
    }
    public class ViewHolder {
        TextView songId,songName;
        public ViewHolder(View view)
        {
            songId = (TextView) view.findViewById(R.id.song_number);
            songName = (TextView) view.findViewById(R.id.song_name);
        }
    }
}
