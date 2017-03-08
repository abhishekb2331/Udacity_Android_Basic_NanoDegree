package com.abhishek.inventoryapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;

public class ResizingImageView extends ImageView{
    private int mMaxHeight;
    private int mMaxWidth;
    public ResizingImageView(Context context)
    {
        super(context);
    }
    public ResizingImageView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
    }
    public ResizingImageView(Context context, AttributeSet attributeSet, int defStyle){
        super(context,attributeSet,defStyle);
    }

    @Override
    public void setMaxWidth(int maxWidth) {
        super.setMaxWidth(maxWidth);
        mMaxWidth=maxWidth;
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        super.setMaxHeight(maxHeight);
        mMaxHeight=maxHeight;
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Drawable drawable = getDrawable();
        if(drawable!=null)
        {
            int wMode = MeasureSpec.getMode(widthMeasureSpec);
            int hMode = MeasureSpec.getMode(heightMeasureSpec);
            if (wMode == MeasureSpec.EXACTLY || hMode == MeasureSpec.EXACTLY) {
                return;
            }
            int maxWidth = wMode == MeasureSpec.AT_MOST
                    ? Math.min(MeasureSpec.getSize(widthMeasureSpec), mMaxWidth)
                    : mMaxWidth;
            int maxHeight = hMode == MeasureSpec.AT_MOST
                    ? Math.min(MeasureSpec.getSize(heightMeasureSpec), mMaxHeight)
                    : mMaxHeight;

            int dWidth = (int)dipToPixels(getContext(),drawable.getIntrinsicWidth());
            int dHeight = (int) dipToPixels(getContext(),drawable.getIntrinsicHeight());
            float ratio = ((float) dWidth) / dHeight;

            int width = Math.min(Math.max(dWidth, getSuggestedMinimumWidth()), maxWidth);
            int height = (int) (width / ratio);

            height = Math.min(Math.max(height, getSuggestedMinimumHeight()), maxHeight);
            width = (int) (height * ratio);

            if (width > maxWidth) {
                width = maxWidth;
                height = (int) (width / ratio);
            }

            setMeasuredDimension(width, height);
        }
    }
}
