package com.example.usermodual.ui.notice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.usermodual.R;

import java.util.ArrayList;

public class NoticeAdpter extends RecyclerView.Adapter<NoticeAdpter.NoticeviewAdpter> {

    Context context;
    ArrayList<Notice> list;

    public NoticeAdpter(Context context, ArrayList<Notice> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeviewAdpter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.needfeeditemlayout, parent, false);

        return new NoticeviewAdpter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeviewAdpter holder, @SuppressLint("RecyclerView") int position) {

        Notice currentitem = list.get(position);
        holder.deletenoticetitle.setText(currentitem.getTitle());
        holder.date.setText(currentitem.getData());
        holder.time.setText(currentitem.getTime());
        try {
            if (currentitem.getImage() != null) {
                Glide.with(context).load(currentitem.getImage()).into(holder.deletenoticeImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.deletenoticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Fullimage.class);
                intent.putExtra("image",currentitem.getImage());
                context.startActivity(intent);
;            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeviewAdpter extends RecyclerView.ViewHolder {

        TextView deletenoticetitle, date, time;
        ImageView deletenoticeImage;

        public NoticeviewAdpter(@NonNull View itemView) {
            super(itemView);

            deletenoticeImage = itemView.findViewById(R.id.deletenoticeImage);
            deletenoticetitle = itemView.findViewById(R.id.deltenoticetitle);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);

        }
    }
}
