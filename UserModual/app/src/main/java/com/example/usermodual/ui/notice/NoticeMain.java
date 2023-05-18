package com.example.usermodual.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.usermodual.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NoticeMain extends Fragment {


    RecyclerView deletenoticeRecyclerview;
    ProgressBar pd;
    List<Notice> list;
    NoticeAdpter adpter;

    DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        deletenoticeRecyclerview = view.findViewById(R.id.deltenoticerectcler);
        pd = view.findViewById(R.id.progrbar);

        reference  = FirebaseDatabase.getInstance().getReference().child("Notice");

        deletenoticeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        deletenoticeRecyclerview.setHasFixedSize(true);

        getNOtice();


        return view;
    }

    private void getNOtice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list = new ArrayList<>();

                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    Notice data = snapshot.getValue(Notice.class);
                    list.add(0,data);

                }
                adpter = new NoticeAdpter(getContext(), (ArrayList<Notice>) list);
                adpter.notifyDataSetChanged();
                pd.setVisibility(View.GONE);
                deletenoticeRecyclerview.setAdapter(adpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                pd.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    }
