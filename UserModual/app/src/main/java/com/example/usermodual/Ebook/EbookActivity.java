package com.example.usermodual.Ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.usermodual.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EbookActivity extends AppCompatActivity {

    RecyclerView ebookREcycler;
    DatabaseReference reference;
    List<EbookData> ebookData;
    ShimmerFrameLayout shimmerFrameLayout;
    EbookAdapter adapter;
    LinearLayout shimaerlayouy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

         shimmerFrameLayout = findViewById(R.id.shimmer);
        shimaerlayouy = findViewById(R.id.shimmerlayout);
        ebookREcycler = findViewById(R.id.ebookrecycler);
        reference = FirebaseDatabase.getInstance().getReference().child("pdf");
        getData();
    }

    private void getData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ebookData = new ArrayList<>();

                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    EbookData data = dataSnapshot.getValue(EbookData.class);
                    ebookData.add(data);
                }
                adapter = new EbookAdapter(EbookActivity.this,ebookData);
                ebookREcycler.setLayoutManager(new LinearLayoutManager(EbookActivity.this));
                ebookREcycler.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                shimaerlayouy.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EbookActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }
}