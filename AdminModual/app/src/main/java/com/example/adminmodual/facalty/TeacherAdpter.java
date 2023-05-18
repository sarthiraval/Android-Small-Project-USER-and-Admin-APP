package com.example.adminmodual.facalty;

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

import com.example.adminmodual.Teacher;
import com.example.adminmodual.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdpter  extends RecyclerView.Adapter<TeacherAdpter.TeeacheViewAdprter> {

    private List<Teacher> list;

    private Context context;
    private  String category;

    public TeacherAdpter(List<Teacher> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category = category;

    }

    @NonNull
    @Override
    public TeeacheViewAdprter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(context).inflate(R.layout.facltyitemteacher,parent,false);
     return new TeeacheViewAdprter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeeacheViewAdprter holder, int position) {

        Teacher item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try{
        Picasso.get().load(item.getImage()).into(holder.imageView);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,UpdateTeacher.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("post",item.getPost());
                intent.putExtra("category",category);
                intent.putExtra("image",item.getImage());
                intent.putExtra("key",item.getKey());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeeacheViewAdprter extends RecyclerView.ViewHolder {

        TextView name,email,post;
        Button update;
        ImageView imageView;
        public TeeacheViewAdprter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teachername);
            email = itemView.findViewById(R.id.teacheremail);
            post = itemView.findViewById(R.id.teacherpost);
            update = itemView.findViewById(R.id.teacherUpdate);
            imageView = itemView.findViewById(R.id.teacherimage);
        }
    }
}
