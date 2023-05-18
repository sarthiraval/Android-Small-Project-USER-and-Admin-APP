package com.example.usermodual.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.usermodual.R;
import com.example.usermodual.Views.SliderViewLayout;
import com.example.usermodual.beans.DrawableImage;
import com.example.usermodual.beans.SliderImage;
import com.example.usermodual.beans.UrlImage;

import java.util.ArrayList;

public class home extends Fragment {
    private ArrayList<SliderImage> imageList = new ArrayList<>();
    ImageView map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        SliderViewLayout sliderViewLayout = view.findViewById(R.id.sliderLayout);

        UrlImage urlImage1 = new UrlImage("http://i.dailymail.co.uk/i/pix/2016/09/06/11/37F60FD200000578-0-image-a-5_1473156426673.jpg");
        imageList.add(urlImage1);
        UrlImage urlImage2 = new UrlImage("https://www.w3schools.com/w3images/lights.jpg");
        imageList.add(urlImage2);
        UrlImage urlImage3 = new UrlImage("https://www.w3schools.com/w3images/lights.jpg");
        imageList.add(urlImage3);

        sliderViewLayout.setSliderImages(imageList, 0);

     //   sliderViewLayout.setCustomIndicators(ContextCompat.getDrawable(getActivity(),R.drawable.indicator_square_selected),ContextCompat.getDrawable(getActivity(),R.drawable.indicator_square_unselected));
       // sliderViewLayout.setCustomArrows(R.drawable.ic_custom_left,R.drawable.ic_custom_right);
      // DrawableImage drawableImage1 = new DrawableImage(R.drawable.ic_home);

        map = view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmap();
            }
        });
        return view;

    }

    private void openmap() {
         String uri="geo:0,0?q=Surya Housing Appartment-3 Naranpura Ahmedabad Gujarat";
      //  String uri ="https://www.google.com/maps/place/Surya+Housing+Appartment,+Naranpura,+Ahmedabad,+Gujarat+380063/@23.0571989,72.5437469,17z/data=!3m1!4b1!4m5!3m4!1s0x395e849fa392529f:0x8b40240cde484669!8m2!3d23.0571989!4d72.5459356?hl=en-GB";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

}