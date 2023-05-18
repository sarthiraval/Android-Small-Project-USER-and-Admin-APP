package com.example.usermodual.Views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.usermodual.OnImageChangeListener;

import java.util.ArrayList;


public class IndicatorLayout extends LinearLayout implements OnImageChangeListener {
    private Context context;
    private int indicatorSize, imgCount;
    private ArrayList<IndicatorView> indicatorList = new ArrayList<>();
    private Drawable customSelectedIndicator, customUnselectedIndicator;

    public IndicatorLayout(Context context, int indicatorSize) {
        super(context);
        this.context = context;
        this.indicatorSize = indicatorSize;
        customUnselectedIndicator = null;
        customSelectedIndicator = null;
        setLayout();
    }

    private void setLayout() {
        setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, indicatorSize * 2);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        layoutParams.setMargins(0, 4, 0, 4);
        setLayoutParams(layoutParams);
    }

    @Override
    public void onImageChanged(int currentImgPos) {
        for (int i = 0; i < indicatorList.size(); i++) {
            if (i == currentImgPos) {
                indicatorList.get(i).onSelectedChange(true);
            } else {
                indicatorList.get(i).onSelectedChange(false);
            }
        }
    }


    private void addIndicator() {
        IndicatorView indicatorView;
        if (customSelectedIndicator != null && customUnselectedIndicator != null) {
            indicatorView = new IndicatorView(context, indicatorSize) {
                @Override
                public void onSelectedChange(boolean isSelected) {
                    super.onSelectedChange(isSelected);
                    if (isSelected) {
                        setBackground(customSelectedIndicator);
                    } else {
                        setBackground(customUnselectedIndicator);
                    }
                }
            };
            indicatorView.setBackground(customUnselectedIndicator);
            indicatorList.add(indicatorView);
            addView(indicatorView);
        } else {
            indicatorView = new IndicatorView(context, indicatorSize);
            indicatorList.add(indicatorView);
            addView(indicatorView);
        }
    }

    public void changeIndicator(Drawable selectedIndicator, Drawable unselectedIndicator) {
        customSelectedIndicator = selectedIndicator;
        customUnselectedIndicator = unselectedIndicator;
        //reset the indicatorlist everytime the user specifies a custom indicator
        setImages(imgCount);
    }

    public void setImages(int imgCount) {
        removeAllViews();
        indicatorList.clear();
        this.imgCount = 0;
        for (int i = 0; i < imgCount; i++) {
            onImageAdded();
        }
        this.imgCount = imgCount;
    }

    public void onImageAdded() {
        this.imgCount += 1;
        addIndicator();
    }

}
