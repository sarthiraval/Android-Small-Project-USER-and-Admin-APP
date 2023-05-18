package com.example.usermodual.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.usermodual.R;

import java.util.ArrayList;
import java.util.List;

public class About extends Fragment {

    ViewPager viewPager;
    private BranchAdpter adpter;
    List<BranchModel> list;
    ImageView map,whatsaap,facebook,linkdian,github,twitter,instagram;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_about, container, false);

        whatsaap = view.findViewById(R.id.whatsapp);
        facebook = view.findViewById(R.id.facebook);
        linkdian = view.findViewById(R.id.linkdian);
        github = view.findViewById(R.id.github);
        twitter = view.findViewById(R.id.twitter);
        instagram =view.findViewById(R.id.instagram);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sharerUrl = "https://www.facebook.com/sarthi.raval.50/" ;
                Intent  intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sharerUrl = "https://github.com/sarthiraval" ;
                Intent  intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sharerUrl = "https://www.instagram.com/r.sarthi/" ;
                Intent  intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sharerUrl = "https://twitter.com/RavalSarthi" ;
                Intent  intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);            }
        });

        whatsaap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String headerReceiver = "";
                    String bodyMessageFormal = "";
                    String whatsappContain = headerReceiver + bodyMessageFormal;
                    String trimToNumner = "+919726760701"; //10 digit number
                    Intent intent = new Intent ( Intent.ACTION_VIEW );
                    intent.setData ( Uri.parse ( "https://wa.me/" + trimToNumner + "/?text=" + "" ) );
                    startActivity ( intent );
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        });

        linkdian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sharerUrl = "https://www.linkedin.com/in/sarthi-raval-791705184/" ;
                Intent  intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                startActivity(intent);
            }

        });

        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.acount,"Account","Accountancy is the art of communicating financial information about a business entity to users such as shareholders and managers. It is a numerical based subject and you need to have good command over ledgers, debiting and crediting balance sheets and even journal entities."));
        list.add(new BranchModel(R.drawable.sketch,"State","Statistics simply means numerical data, and is field of math that generally deals with collection of data, tabulation, and interpretation of numerical data. It is actually a form of mathematical analysis that uses different quantitative models to produce a set of experimental data or studies of real life."));
        list.add(new BranchModel(R.drawable.eco,"ECO","English is a universal subject that exists irrespective of the group that you choose. You have studied the subject and basic grammar until your 10th standard, and this is not a new shoe to fit wrong."));
        list.add(new BranchModel(R.drawable.ba,"BA","In simple terms, business administration is the work of managing an organization's resources, time and people. Business administration professionals work to ensure that businesses and organizations are run effectively, efficiently and profitably."));
        list.add(new BranchModel(R.drawable.english,"English","English is a language—originally the language of the people of England. Today, English is the main language of the United Kingdom, Ireland, the United States of America, Canada, Australia, New Zealand and more than fifty other countries."));
        list.add(new BranchModel(R.drawable.gujarati,"Gujarati","Gujarati is a language spoken in western India, particularly in the state of Gujarat, which is located on India’s western coast.Gujarati is an Indic language, coming from the same language family as Sanskrit, Hindi, Bengali, and many other languages spoken in India, Pakistan, and Sri Lanka. "));
        map = view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmap();
            }
        });
        adpter = new BranchAdpter(getContext(),list);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adpter);
        ImageView imageView = view.findViewById(R.id.collageimage);
        Glide.with(getContext())
                .load(R.drawable.ssr)
                .into(imageView);
        return  view;

    }
    private void openmap() {
        String uri="geo:0,0?q=Surya Housing Appartment-3 Naranpura Ahmedabad Gujarat";
        //  String uri ="https://www.google.com/maps/place/Surya+Housing+Appartment,+Naranpura,+Ahmedabad,+Gujarat+380063/@23.0571989,72.5437469,17z/data=!3m1!4b1!4m5!3m4!1s0x395e849fa392529f:0x8b40240cde484669!8m2!3d23.0571989!4d72.5459356?hl=en-GB";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}