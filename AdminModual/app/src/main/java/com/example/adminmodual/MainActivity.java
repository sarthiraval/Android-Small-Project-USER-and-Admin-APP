package com.example.adminmodual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.adminmodual.delete.DeleteNotice;
import com.example.adminmodual.facalty.UpdateFactluy;


public class MainActivity extends AppCompatActivity {

    CardView uploadnotice,addgallery,pdf,factlty,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdf = findViewById(R.id.uploadpdf);
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UploadPdf.class);
                startActivity(intent);


            }
        });
        uploadnotice = findViewById(R.id.addnotice);
        uploadnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UploadNotic.class);
                startActivity(intent);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bottom_to_up);
                uploadnotice.startAnimation(animation);
            }
        });
        factlty = findViewById(R.id.groupfactlyu);
        factlty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateFactluy.class);
                startActivity(intent);

            }
        });
        addgallery = findViewById(R.id.addgallry);
        addgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UploadImage.class);
                startActivity(intent);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bottom_to_up);
                addgallery.startAnimation(animation);
            }
        });
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteNotice.class);
                startActivity(intent);
                    }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}