package com.example.usermodual.ui.factly;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.usermodual.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdpter  extends RecyclerView.Adapter<TeacherAdpter.TeeacheViewAdprter> {

    private List<Teacher> list;

    private Context context;

    public TeacherAdpter(List<Teacher> list, Context context) {
        this.list = list;
        this.context = context;


    }

    @NonNull
    @Override
    public TeeacheViewAdprter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(context).inflate(R.layout.factly_item_teacher,parent,false);
     return new TeeacheViewAdprter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeeacheViewAdprter holder, int position) {

        Teacher item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try{
        Glide.with(context).load(item.getImage()).placeholder(R.drawable.profile).into(holder.imageView);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeeacheViewAdprter extends RecyclerView.ViewHolder {

        TextView name,email,post;
        ImageView imageView;
        public TeeacheViewAdprter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teachername);
            email = itemView.findViewById(R.id.teacheremail);
            post = itemView.findViewById(R.id.teacherpost);
            imageView = itemView.findViewById(R.id.teacherimage);
        }
    }
}
