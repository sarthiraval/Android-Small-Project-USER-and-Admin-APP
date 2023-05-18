package com.example.usermodual.ui.factly;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.usermodual.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class FacultyMain extends Fragment {

    DatabaseReference reference,dbref;
    StorageReference storageReference;
    RecyclerView account,ba,eco,english,state,gujarti;
    LinearLayout accountliner,baliner,ecoliner,englishliner,stateliner,gujratliliner;
    List<Teacher> list1,list2,list3,list4,list5,list6;
    TeacherAdpter adpter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);
        reference = FirebaseDatabase.getInstance().getReference().child("Teacher");
        storageReference = FirebaseStorage.getInstance().getReference();

        account = view.findViewById(R.id.accountDepartmant);
        ba = view.findViewById(R.id.baDepartmant);
        eco = view.findViewById(R.id.ecoDepartmant);
        english = view.findViewById(R.id.englishDepartmant);
        state = view.findViewById(R.id.stateDepartmant);
        gujarti = view.findViewById(R.id.gujratiDepartmant);

        accountliner = view.findViewById(R.id.accountNodata);
        baliner = view.findViewById(R.id.baNodata);
        ecoliner = view.findViewById(R.id.ecoNodata);
        englishliner = view.findViewById(R.id.englishNodata);
        stateliner = view.findViewById(R.id.stateNodata);
        gujratliliner = view.findViewById(R.id.gujratiNodata);

        accountdep();
        badep();
        ecodp();
        englishdep();
        statedep();
        gujaratidep();

   return view;
    }


    private void accountdep() {

        dbref = reference.child("Account");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list1 = new ArrayList<>();
                if (!datasnapshot.exists()){
                    accountliner.setVisibility(View.VISIBLE);
                    account.setVisibility(View.GONE);
                }else{
                    accountliner.setVisibility(View.GONE);
                    account.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot : datasnapshot.getChildren()){
                        Teacher data = snapshot.getValue(Teacher.class);
                        list1.add(data);

                    }
                    account.setHasFixedSize(true);
                    account.setLayoutManager(new LinearLayoutManager(getContext()));
                    adpter = new TeacherAdpter(list1,getContext());
                    account.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void statedep() {
        dbref = reference.child("State");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list2 = new ArrayList<>();
                if (!datasnapshot.exists()){
                    stateliner.setVisibility(View.VISIBLE);
                    state.setVisibility(View.GONE);
                }else{
                    stateliner.setVisibility(View.GONE);
                    state.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot : datasnapshot.getChildren()){
                        Teacher data = snapshot.getValue(Teacher.class);
                        list2.add(data);

                    }
                    state.setHasFixedSize(true);
                    state.setLayoutManager(new LinearLayoutManager(getContext()));
                    adpter = new TeacherAdpter(list2,getContext());
                    state.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void englishdep() {
        dbref = reference.child("English");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list3 = new ArrayList<>();
                if (!datasnapshot.exists()){
                    englishliner.setVisibility(View.VISIBLE);
                    english.setVisibility(View.GONE);
                }else{
                    englishliner.setVisibility(View.GONE);
                    english.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot : datasnapshot.getChildren()){
                        Teacher data = snapshot.getValue(Teacher.class);
                        list3.add(data);

                    }
                    english.setHasFixedSize(true);
                    english.setLayoutManager(new LinearLayoutManager(getContext()));
                    adpter = new TeacherAdpter(list3,getContext());
                    english.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void ecodp() {
        dbref = reference.child("Eco");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list4 = new ArrayList<>();
                if (!datasnapshot.exists()){
                    ecoliner.setVisibility(View.VISIBLE);
                    eco.setVisibility(View.GONE);
                }else{
                    ecoliner.setVisibility(View.GONE);
                    eco.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot : datasnapshot.getChildren()){
                        Teacher data = snapshot.getValue(Teacher.class);
                        list4.add(data);

                    }
                    eco.setHasFixedSize(true);
                    eco.setLayoutManager(new LinearLayoutManager(getContext()));
                    adpter = new TeacherAdpter(list4,getContext());
                    eco.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void badep() {
        dbref = reference.child("BA");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list5 = new ArrayList<>();
                if (!datasnapshot.exists()){
                    baliner.setVisibility(View.VISIBLE);
                    ba.setVisibility(View.GONE);
                }else{
                    baliner.setVisibility(View.GONE);
                    ba.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot : datasnapshot.getChildren()){
                        Teacher data = snapshot.getValue(Teacher.class);
                        list5.add(data);

                    }
                    ba.setHasFixedSize(true);
                    ba.setLayoutManager(new LinearLayoutManager(getContext()));
                    adpter = new TeacherAdpter(list5,getContext());
                    ba.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void gujaratidep() {
        dbref = reference.child("Gujarati");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list6 = new ArrayList<>();
                if (!datasnapshot.exists()){
                    gujratliliner.setVisibility(View.VISIBLE);
                    gujarti.setVisibility(View.GONE);
                }else{
                    gujratliliner.setVisibility(View.GONE);
                    gujarti.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot : datasnapshot.getChildren()){
                        Teacher data = snapshot.getValue(Teacher.class);
                        list6.add(data);

                    }
                    gujarti.setHasFixedSize(true);
                    gujarti.setLayoutManager(new LinearLayoutManager(getContext()));
                    adpter = new TeacherAdpter(list6,getContext());
                    gujarti.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}