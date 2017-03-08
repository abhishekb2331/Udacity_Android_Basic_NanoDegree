package com.abhishek.tourguide;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.abhishek.tourguide.adapter.TourGuideAdapter;
import com.abhishek.tourguide.model.TourGuideItem;

import java.util.ArrayList;

public class HotelFragment extends Fragment{
    private ListView mListView;

    private ShowMoreFragment.ShowMoreInterface showMoreInterface;

    @Override
    public void onAttach(Context context) {
        showMoreInterface = (ShowMoreFragment.ShowMoreInterface) getActivity();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment,container,false);
        mListView= (ListView) mView.findViewById(R.id.list_item);
        final ArrayList<TourGuideItem> mItem = new ArrayList<TourGuideItem>();

        mItem.add(new TourGuideItem(R.string.HOTEL1_TITLE,R.string.HOTEL1_LOCATION,R.string.HOTEL1_DESC,R.drawable.oberoi_hotel));
        mItem.add(new TourGuideItem(R.string.HOTEL2_TITLE,R.string.HOTEL2_LOCATION,R.string.HOTEL2_DESC,R.drawable.tajmahal_palace_hotel));
        mItem.add(new TourGuideItem(R.string.HOTEL3_TITLE,R.string.HOTEL3_LOCATION,R.string.HOTEL3_DESC,R.drawable.watson_hotel));
        mItem.add(new TourGuideItem(R.string.HOTEL4_TITLE,R.string.HOTEL4_LOCATION,R.string.HOTEL4_DESC,R.drawable.four_seasons));

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
