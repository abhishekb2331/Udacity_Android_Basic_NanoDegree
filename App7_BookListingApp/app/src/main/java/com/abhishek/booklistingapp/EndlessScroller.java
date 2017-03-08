package com.abhishek.booklistingapp;

import android.util.Log;
import android.widget.AbsListView;

public abstract class EndlessScroller implements AbsListView.OnScrollListener {

    private int visibleThreshold=10;
    private int startingPageIndex=0;
    private int curPageIndex=0;
    private int prevTotalItem=0;
    private boolean loading =true;
    public  EndlessScroller()
    {
    }
    public EndlessScroller(int visibleThreshold)
    {
        this.visibleThreshold=visibleThreshold;
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


        if (totalItemCount < prevTotalItem) {
            this.curPageIndex = this.startingPageIndex;
            this.prevTotalItem = totalItemCount;
            if (totalItemCount == 0) { this.loading = true; }
        }

        if(loading && prevTotalItem<totalItemCount)
        {
            loading=false;
            prevTotalItem=totalItemCount;
            curPageIndex++;
        }

        if((!loading)&&(firstVisibleItem+visibleItemCount+visibleThreshold>=totalItemCount))
        {
            loading=onLoadMore(curPageIndex,totalItemCount);
        }
    }
    public void resetState() {
        this.curPageIndex = this.startingPageIndex;
        this.prevTotalItem = 0;
        this.loading = true;
    }
    public void setLoading(boolean loading) {
     //   Log.v("Endless Scroller","initial value" + loading);
        this.loading = loading;
    }

    public abstract boolean onLoadMore(int curPageIndex, int totalItemCount);
}
