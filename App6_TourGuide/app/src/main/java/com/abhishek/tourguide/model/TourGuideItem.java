package com.abhishek.tourguide.model;

import android.provider.ContactsContract;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class TourGuideItem {

    private int mImageResourceId;
    private int mDesc;
    private int mLocation;
    private int mTitle;

    public TourGuideItem()
    {

    }
    public TourGuideItem(int Title,int Location,int Desc,int Image)
    {
        mDesc=Desc;
        mTitle=Title;
        mLocation=Location;
        mImageResourceId=Image;
    }

    public TourGuideItem(int Title,int Location,int Desc)
    {
        mDesc=Desc;
        mTitle=Title;
        mLocation=Location;
        mImageResourceId=-1;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getDesc() {
        return mDesc;
    }

    public int getLocation() {
        return mLocation;
    }

    public int getTitle() {
        return mTitle;
    }

    public void setDesc(int mDesc) {
        this.mDesc = mDesc;
    }
    public void setImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    public void setLocation(int mLocation) {
        this.mLocation = mLocation;
    }

    public void setTitle(int mTitle) {
        this.mTitle = mTitle;
    }
}
