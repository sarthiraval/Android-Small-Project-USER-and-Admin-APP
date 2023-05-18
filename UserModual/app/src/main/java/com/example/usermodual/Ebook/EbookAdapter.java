package com.example.usermodual.Ebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermodual.R;
import com.example.usermodual.pdf.PdfViewer;

import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewholder> {

    private Context context;
    private List<EbookData> list;

    public EbookAdapter(Context context, List<EbookData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EbookViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.ebbokitemlayout,parent,false);
        return new EbookViewholder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull EbookViewholder holder, final int position) {

        holder.textView.setText(list.get(position).getPdfTitle());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getPdfUrl()));
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PdfViewer.class);
                intent.putExtra("pdfUrl",list.get(position).getPdfUrl());
                context.startActivity(intent);
                Toast.makeText(context, list.get(position).getPdfTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class EbookViewholder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        public EbookViewholder(@NonNull View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.pdfname);
            imageView = itemView.findViewById(R.id.pdfimg);

        }
    }
}
