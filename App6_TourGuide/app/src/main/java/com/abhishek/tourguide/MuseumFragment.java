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

public class MuseumFragment extends Fragment {
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

        mItem.add(new TourGuideItem(R.string.Museum1_TITLE,R.string.Museum1_LOCATION,R.string.Museum1_DESC,R.drawable.cowasji_jehangir));
        mItem.add(new TourGuideItem(R.string.Museum2_TITLE,R.string.Museum2_LOCATION,R.string.Museum2_DESC,R.drawable.drbhau_daji_laad_museum_facade));
        mItem.add(new TourGuideItem(R.string.Museum3_TITLE,R.string.Museum3_LOCATION,R.string.Museum3_DESC,R.drawable.national_gallery_of_modern_art));
        mItem.add(new TourGuideItem(R.string.Museum4_TITLE,R.string.Museum4_LOCATION,R.string.Museum4_DESC,R.drawable.nehru_science_center));
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
