package com.example.usermodual.ui.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usermodual.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class gallery extends Fragment {


    RecyclerView indepdance,other,convaction;
    GalleryAdpter galleryAdpter;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gallery, container, false);
        convaction = view.findViewById(R.id.convactionRecylcer);
        other = view.findViewById(R.id.otherRecylcer);
        indepdance = view.findViewById(R.id.indepedanceDayRecylcer);

        reference = FirebaseDatabase.getInstance().getReference().child("gallery");
        getconvoImage();
        getOtherImage();
        getIndependaceImage();

        return view;
    }

    private void getconvoImage() {
        reference.child("Convocation").addValueEventListener(new ValueEventListener() {
            List<String> imaglist = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot :datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imaglist.add(data);
                }
                galleryAdpter = new GalleryAdpter(getContext(),imaglist);
                convaction.setLayoutManager(new GridLayoutManager(getContext(),3));
                convaction.setAdapter(galleryAdpter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getOtherImage() {
        reference.child("Other Events").addValueEventListener(new ValueEventListener() {
            List<String> imaglist = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot :datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imaglist.add(data);
                }
                galleryAdpter = new GalleryAdpter(getContext(),imaglist);
                other.setLayoutManager(new GridLayoutManager(getContext(),3));
                other.setAdapter(galleryAdpter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getIndependaceImage() {
        reference.child("Indepedance Day").addValueEventListener(new ValueEventListener() {
            List<String> imaglist = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot :datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imaglist.add(data);
                }
                galleryAdpter = new GalleryAdpter(getContext(),imaglist);
                indepdance.setLayoutManager(new GridLayoutManager(getContext(),3));
                indepdance.setAdapter(galleryAdpter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}