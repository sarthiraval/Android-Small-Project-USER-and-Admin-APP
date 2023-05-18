package com.example.adminmodual.delete;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminmodual.Notice;
import com.example.adminmodual.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

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
        View view = LayoutInflater.from(context).inflate(R.layout.needfeeditemlayout,parent,false);

        return new NoticeviewAdpter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeviewAdpter holder, @SuppressLint("RecyclerView") int position) {

        Notice currentitem = list.get(position);
        holder.deletenoticetitle.setText(currentitem.getTitle());
        try{
            if (currentitem.getImage() != null){
                Picasso.get().load(currentitem.getImage()).into(holder.deletenoticeImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.deltenoticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder  builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure want to delete this notice ?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notice");
                                reference.child(currentitem.getKey()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context, "Deleted !!!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                notifyItemRemoved(position);
                            }
                        }
                );

                builder.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );
                AlertDialog  dialog=null;
                try {
                    dialog = builder.create();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dialog != null){
                dialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeviewAdpter extends RecyclerView.ViewHolder{

        Button deltenoticebtn;
        TextView deletenoticetitle;
        ImageView deletenoticeImage;

        public NoticeviewAdpter(@NonNull View itemView) {
            super(itemView);

            deletenoticeImage =  itemView.findViewById(R.id.deletenoticeImage);
            deletenoticetitle = itemView.findViewById(R.id.deltenoticetitle);
            deltenoticebtn = itemView.findViewById(R.id.deltenoticebtn);

        }
    }
}
