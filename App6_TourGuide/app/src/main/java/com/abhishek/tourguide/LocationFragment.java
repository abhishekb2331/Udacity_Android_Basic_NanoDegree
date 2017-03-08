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

public class LocationFragment extends Fragment{
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
        mItem.add(new TourGuideItem(R.string.IMPLocation1_TITLE,R.string.IMPLOCATION1_LOCATION,R.string.IMPLOCATION1_DESC,R.drawable.gateway_of_india));
        mItem.add(new TourGuideItem(R.string.IMPLocation2_TITLE,R.string.IMPLOCATION2_LOCATION,R.string.IMPLOCATION2_DESC,R.drawable.cave_of_elephants));
        mItem.add(new TourGuideItem(R.string.IMPLocation3_TITLE,R.string.IMPLOCATION3_LOCATION,R.string.IMPLOCATION3_DESC,R.drawable.sanjay_gandhi_national_park));
        mItem.add(new TourGuideItem(R.string.IMPLocation4_TITLE,R.string.IMPLOCATION4_LOCATION,R.string.IMPLOCATION4_DESC,R.drawable.worli_fort));
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
