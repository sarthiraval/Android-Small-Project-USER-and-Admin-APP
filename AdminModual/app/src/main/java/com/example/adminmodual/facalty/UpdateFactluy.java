package com.example.adminmodual.facalty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.adminmodual.Teacher;
import com.example.adminmodual.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UpdateFactluy extends AppCompatActivity {

    FloatingActionButton fab;

    DatabaseReference reference,dbref;
    StorageReference storageReference;
    RecyclerView account,ba,eco,english,state,gujarti;
    LinearLayout accountliner,baliner,ecoliner,englishliner,stateliner,gujratliliner;
    List<Teacher> list1,list2,list3,list4,list5,list6;
    TeacherAdpter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_factluy);

        reference = FirebaseDatabase.getInstance().getReference().child("Teacher");
        storageReference = FirebaseStorage.getInstance().getReference();

        account = findViewById(R.id.accountDepartmant);
        ba = findViewById(R.id.baDepartmant);
        eco = findViewById(R.id.ecoDepartmant);
        english = findViewById(R.id.englishDepartmant);
        state = findViewById(R.id.stateDepartmant);
        gujarti = findViewById(R.id.gujratiDepartmant);

        accountliner = findViewById(R.id.accountNodata);
        baliner = findViewById(R.id.baNodata);
        ecoliner = findViewById(R.id.ecoNodata);
        englishliner = findViewById(R.id.englishNodata);
        stateliner = findViewById(R.id.stateNodata);
        gujratliliner = findViewById(R.id.gujratiNodata);

        accountdep();
        badep();
        ecodp();
        englishdep();
        statedep();
        gujaratidep();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateFactluy.this,AddTeacher.class));
            }
        });

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
                    account.setLayoutManager(new LinearLayoutManager(UpdateFactluy.this));
                    adpter = new TeacherAdpter(list1,UpdateFactluy.this,"Account");
                    account.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UpdateFactluy.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    state.setLayoutManager(new LinearLayoutManager(UpdateFactluy.this));
                    adpter = new TeacherAdpter(list2,UpdateFactluy.this,"State");
                    state.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UpdateFactluy.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    english.setLayoutManager(new LinearLayoutManager(UpdateFactluy.this));
                    adpter = new TeacherAdpter(list3,UpdateFactluy.this,"English");
                    english.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UpdateFactluy.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    eco.setLayoutManager(new LinearLayoutManager(UpdateFactluy.this));
                    adpter = new TeacherAdpter(list4,UpdateFactluy.this,"Eco");
                    eco.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UpdateFactluy.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    ba.setLayoutManager(new LinearLayoutManager(UpdateFactluy.this));
                    adpter = new TeacherAdpter(list5,UpdateFactluy.this,"BA");
                    ba.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UpdateFactluy.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    gujarti.setLayoutManager(new LinearLayoutManager(UpdateFactluy.this));
                    adpter = new TeacherAdpter(list6,UpdateFactluy.this,"Gujarati");
                    gujarti.setAdapter(adpter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(UpdateFactluy.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}