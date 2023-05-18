package com.example.usermodual.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.usermodual.R;
import com.example.usermodual.ui.notice.Fullimage;

import java.util.List;

public class GalleryAdpter  extends RecyclerView.Adapter<GalleryAdpter.GalleryViewAdpter> {

    Context context;
    List<String> images;

    public GalleryAdpter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public GalleryViewAdpter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_image,parent,false);
        return new GalleryViewAdpter(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull GalleryViewAdpter holder,final int position) {

        Glide.with(context).load(images.get(position)).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Fullimage.class);
                intent.putExtra("image",images.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class GalleryViewAdpter extends RecyclerView.ViewHolder {

        ImageView imageView;

        public GalleryViewAdpter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

}
