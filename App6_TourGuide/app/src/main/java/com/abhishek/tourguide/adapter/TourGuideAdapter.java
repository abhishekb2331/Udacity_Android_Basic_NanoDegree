package com.abhishek.tourguide.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.tourguide.R;
import com.abhishek.tourguide.model.TourGuideItem;
import java.util.ArrayList;

public class TourGuideAdapter extends ArrayAdapter<TourGuideItem> {

    public TourGuideAdapter(Context context, ArrayList<TourGuideItem> tourGuideItems)
    {
        super(context,0,tourGuideItems);
    }
    public static class viewHolder
    {
        ImageView mImageRes;
        TextView mTitle;
        TextView mDesc;
        TextView mLocation;
        public viewHolder(View view)
        {
            mImageRes= (ImageView) view.findViewById(R.id.image);
            mTitle= (TextView) view.findViewById(R.id.title);
            mDesc = (TextView) view.findViewById(R.id.desc);
            mLocation= (TextView) view.findViewById(R.id.location);
        }

        public TextView getDesc() {
            return mDesc;
        }

        public ImageView getImageRes() {
            return mImageRes;
        }

        public TextView getLocation() {
            return mLocation;
        }

        public TextView getTitle() {
            return mTitle;
        }

        public void setDescId(TextView mDesc) {
            this.mDesc = mDesc;
        }

        public void setImageResId(ImageView mImageRes) {
            this.mImageRes = mImageRes;
        }

        public void setLocationId(TextView mLocation) {
            this.mLocation = mLocation;
        }

        public void setTitleId(TextView mTitle) {
            this.mTitle = mTitle;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TourGuideItem mItem = getItem(position);
        viewHolder vh;
        View mView=convertView;
        if(convertView == null)
        {
            mView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
            vh = new viewHolder(mView);
        }
        else
        {
            vh= (viewHolder) convertView.getTag();
        }
        vh.mImageRes.setImageResource(mItem.getImageResourceId());
        vh.mTitle.setText(mItem.getTitle());
        vh.mLocation.setText(mItem.getLocation());
        vh.mDesc.setText(mItem.getDesc());
        mView.setTag(vh);
        return mView;
    }
}
