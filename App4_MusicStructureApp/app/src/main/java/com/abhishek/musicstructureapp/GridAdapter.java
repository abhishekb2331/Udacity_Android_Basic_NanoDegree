package com.abhishek.musicstructureapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private final List<Album> mItems;
    private final LayoutInflater mInflater;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public GridAdapter(Context context,List<Album> albumList)
    {
        mInflater=LayoutInflater.from(context);
        mItems= albumList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        ViewHolder viewHolder;
        view = mInflater.inflate(R.layout.grid_item,parent,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.drawable.setImageResource(mItems.get(position).getDrawableId());
        viewHolder.name.setText(mItems.get(position).getAlbumName());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView name;
        public final ImageView drawable;

        public ViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.album_name);
            this.drawable = (ImageView) view.findViewById(R.id.album_picture);
            view.setOnClickListener(new View.OnClickListener() {
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
