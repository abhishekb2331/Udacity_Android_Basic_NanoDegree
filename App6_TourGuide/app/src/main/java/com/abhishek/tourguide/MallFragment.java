package com.abhishek.tourguide;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.abhishek.tourguide.adapter.TourGuideAdapter;
import com.abhishek.tourguide.model.TourGuideItem;

import java.util.ArrayList;

public class MallFragment extends Fragment{
    private ListView mListView;
    private ShowMoreFragment.ShowMoreInterface showMoreInterface;

    @Override
    public void onAttach(Context context) {
        showMoreInterface = (ShowMoreFragment.ShowMoreInterface) getActivity();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment,container,false);
        mListView= (android.widget.ListView) mView.findViewById(R.id.list_item);
        final ArrayList<TourGuideItem> mItem = new ArrayList<TourGuideItem>();

        mItem.add(new TourGuideItem(R.string.Mall1_TITLE,R.string.Mall1_LOCATION,R.string.Mall1_DESC,R.drawable.inorbit_mall));
        mItem.add(new TourGuideItem(R.string.Mall2_TITLE,R.string.Mall2_LOCATION,R.string.Mall2_DESC,R.drawable.neptune_magnet_mall));
        mItem.add(new TourGuideItem(R.string.Mall3_TITLE,R.string.Mall3_LOCATION,R.string.Mall3_DESC,R.drawable.r_city_mall_ghatkopar));
        mItem.add(new TourGuideItem(R.string.Mall4_TITLE,R.string.Mall4_LOCATION,R.string.Mall4_DESC,R.drawable.metromall));
        TourGuideAdapter tourGuideAdapter = new TourGuideAdapter(getActivity().getApplicationContext(),mItem);
        mListView.setAdapter(tourGuideAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMoreInterface.ShowMore(mItem.get(position));
            }
        });

        return mView;
    }
}
