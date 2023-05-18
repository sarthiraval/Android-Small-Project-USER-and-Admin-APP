package com.example.usermodual.Views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.usermodual.beans.DrawableImage;
import com.example.usermodual.beans.SliderImage;
import com.example.usermodual.beans.UrlImage;

public class ImageFragment extends Fragment {

    public ImageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SliderImage sliderImage = getArguments().getParcelable("sliderImage");
        int placeholder = getArguments().getInt("placeholder");
        if (sliderImage != null) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if (sliderImage instanceof UrlImage) {
                UrlImage urlImage = (UrlImage) sliderImage;
                Glide.with(getContext())
                        .load(urlImage.getImgUrl())
                        .apply(new RequestOptions().placeholder(placeholder))
                        .into(imageView);
            } else {
                DrawableImage drawableImage = (DrawableImage) sliderImage;
                Glide.with(getContext())
                        .load(drawableImage.getDrawable())
                        .apply(new RequestOptions().placeholder(placeholder))
                        .into(imageView);
            }
            return imageView;
        } else {
            throw new RuntimeException("Image is null");
        }
    }

}
