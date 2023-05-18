package com.example.usermodual.ui.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.usermodual.R;
import com.github.chrisbanes.photoview.PhotoView;

public class Fullimage extends AppCompatActivity {

    PhotoView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimage);

        String image = getIntent().getStringExtra("image");
        imageView = findViewById(R.id.imagsds);
        Glide.with(this).load(image).into(imageView);

    }
}