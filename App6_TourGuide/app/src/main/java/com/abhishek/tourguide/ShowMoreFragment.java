package com.abhishek.tourguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.tourguide.model.TourGuideItem;

import org.parceler.Parcels;


public class ShowMoreFragment extends DialogFragment {
    public static final String DIALOG_DATA="dialog_data";

    private ImageView mImageRes;
    private TextView mTitle;
    private TextView mDesc;
    private TextView mLocation;
    public interface  ShowMoreInterface{
        public void ShowMore(TourGuideItem tourGuideItem);
    }
    public ShowMoreFragment()
    {

    }
    public static ShowMoreFragment instance(TourGuideItem item)
    {
        ShowMoreFragment showMoreFragment = new ShowMoreFragment();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(DIALOG_DATA, Parcels.wrap(item));
        showMoreFragment.setArguments(mBundle);
        return showMoreFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.list_item,container,false);
        mImageRes = (ImageView) mView.findViewById(R.id.image);
        mTitle= (TextView) mView.findViewById(R.id.title);
        mLocation= (TextView) mView.findViewById(R.id.location);
        mDesc= (TextView) mView.findViewById(R.id.desc);
        Bundle args = getArguments();
        TourGuideItem tourGuideItem = Parcels.unwrap(args.getParcelable(DIALOG_DATA));
        mDesc.setMaxLines(Integer.MAX_VALUE);
        mDesc.setEllipsize(null);
        mImageRes.setImageResource(tourGuideItem.getImageResourceId());
        mLocation.setText(tourGuideItem.getLocation());
        mDesc.setText(tourGuideItem.getDesc());
        mTitle.setText(tourGuideItem.getTitle());
        getDialog().setCanceledOnTouchOutside(true);
        return mView;
    }
}
