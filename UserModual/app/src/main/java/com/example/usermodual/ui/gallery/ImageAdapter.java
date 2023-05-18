package com.example.usermodual.ui.gallery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.usermodual.R;
import com.example.usermodual.Views.ImageFragment;
import com.example.usermodual.beans.SliderImage;

import java.util.ArrayList;


public class ImageAdapter extends FragmentPagerAdapter {
    private ArrayList<SliderImage> sliderImageArrayList;
    private int placeholder;
    private boolean infiniteView;

    public ImageAdapter(FragmentManager fm, ArrayList<SliderImage> sliderImageList, int placeholder, Boolean infiniteView) {
        super(fm);
        this.sliderImageArrayList = sliderImageList;
        this.placeholder = placeholder;
        this.infiniteView = infiniteView;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        if (placeholder != 0) {
            args.putInt("placeholder", placeholder);
        } else {
            args.putInt("placeholder", R.drawable.ssr);
        }
        if (infiniteView) {
            if (position == 0) {
                args.putParcelable("sliderImage", sliderImageArrayList.get(sliderImageArrayList.size() - 1));
            } else if (position == sliderImageArrayList.size() + 1) {
                args.putParcelable("sliderImage", sliderImageArrayList.get(0));
            } else {
                args.putParcelable("sliderImage", sliderImageArrayList.get(position - 1));
            }
        } else {
            args.putParcelable("sliderImage", sliderImageArrayList.get(position));
        }
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        if (infiniteView) {
            return sliderImageArrayList.size() + 2;
        } else {
            return sliderImageArrayList.size();
        }
    }
}
