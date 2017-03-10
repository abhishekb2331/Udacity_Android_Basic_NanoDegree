package com.abhishek.musicstructureapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int IMAGE = 1;
    private static final int DATA = 2;
    private final LayoutInflater mInflater;
    int drawableId;
    Album album;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AlbumAdapter(Context context, int drawableId,Album album)
    {
        mInflater=LayoutInflater.from(context);
        this.album=album;
        this.drawableId=drawableId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType)
        {
            case IMAGE:
                View view1 = inflater.inflate(R.layout.album_info_cover,parent,false);
                viewHolder = new ViewHolder1(view1);
                return viewHolder;
            case DATA:
                View view2 = inflater.inflate(R.layout.album_info_item,parent,false);
                viewHolder = new ViewHolder2(view2);
                return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType())
        {
            case IMAGE:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                vh1.drawable.setImageResource(drawableId);
                return;
            case DATA:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                vh2.albumName.setText(album.getAlbumName());
                vh2.albumArtist.setText(album.getSinger());
                return;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return IMAGE;
        else
            return DATA;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{
        public final ImageView drawable;

        public ViewHolder1(View view) {
            super(view);
            this.drawable = (ImageView) view.findViewById(R.id.album_picture);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder{
        public final TextView albumName,albumArtist;

        public ViewHolder2(View view) {
            super(view);
            this.albumName = (TextView) view.findViewById(R.id.album_name);
            this.albumArtist = (TextView) view.findViewById(R.id.album_artist);
            this.albumArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }


}