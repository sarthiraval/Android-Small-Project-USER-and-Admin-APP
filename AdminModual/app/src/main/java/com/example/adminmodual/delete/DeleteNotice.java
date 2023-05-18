package com.example.adminmodual.delete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adminmodual.Notice;
import com.example.adminmodual.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteNotice extends AppCompatActivity {

    RecyclerView deletenoticeRecyclerview;
    ProgressBar pd;
    List<Notice> list;
    NoticeAdpter adpter;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);

        deletenoticeRecyclerview = findViewById(R.id.deltenoticerectcler);
        pd = findViewById(R.id.progrbar);

        reference  = FirebaseDatabase.getInstance().getReference().child("Notice");

        deletenoticeRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        deletenoticeRecyclerview.setHasFixedSize(true);

        getNOtice();
    }

    private void getNOtice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    Notice data = snapshot.getValue(Notice.class);
                    list.add(data);

                }
                adpter = new NoticeAdpter(DeleteNotice.this, (ArrayList<Notice>) list);
                adpter.notifyDataSetChanged();
                pd.setVisibility(View.GONE);
                deletenoticeRecyclerview.setAdapter(adpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                pd.setVisibility(View.GONE);
                Toast.makeText(DeleteNotice.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}