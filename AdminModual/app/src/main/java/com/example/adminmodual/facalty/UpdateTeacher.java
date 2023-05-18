package com.example.adminmodual.facalty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adminmodual.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateTeacher extends AppCompatActivity {

    CircleImageView imageview;
    String downloadurl,uniquekey,category;
    EditText tname,temail,tpost;
    Button update,delete;

    DatabaseReference reference,dbref;
    Bitmap bitmap= null;

    StorageReference storageReference;
    private  final int REQ =1;
    ProgressDialog pd;

    private String name,email,image,post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);


        reference = FirebaseDatabase.getInstance().getReference().child("Teacher");
        storageReference = FirebaseStorage.getInstance().getReference().child("Teacher");

        pd = new ProgressDialog(this);


        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");
        post = getIntent().getStringExtra("post");

        uniquekey = getIntent().getStringExtra("key");

        category =  getIntent().getStringExtra("category");

        imageview = findViewById(R.id.updateTeacherImage);
        tname = findViewById(R.id.updateTeacherName);
        temail = findViewById(R.id.updateTeacherEmail);
        tpost = findViewById(R.id.updateTeacherPost);
        update = findViewById(R.id.updatebtn);
        delete = findViewById(R.id.deletebtn);


        try {
            Picasso.get().load(image).into(imageview);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tname.setText(name);
        temail.setText(email);
        tpost.setText(post);

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name= tname.getText().toString();
                email=temail.getText().toString();
                post=tpost.getText().toString();

                checkVailedation();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void deleteData() {

        reference.child(category).child(uniquekey).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UpdateTeacher.this, "Teacher Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateTeacher.this,UpdateFactluy.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkVailedation() {
        if (name.isEmpty()){
            tname.setError("Error");
            tname.requestFocus();
        }else  if (email.isEmpty()){
            temail.setError("Error");
            temail.requestFocus();
        }else  if (post.isEmpty()){
            tpost.setError("Error");
            tpost.requestFocus();
        }else if (bitmap == null){
            uploadData(image);
        }else{
            uploadImage();
        }



    }

    private void uploadImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage = baos.toByteArray();
        final StorageReference filepath= storageReference.child("Teacher").child(finalImage+"jpg");
        final UploadTask uploadTask= filepath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(UpdateTeacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadurl = String.valueOf(uri);
                                    uploadData(downloadurl);
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(UpdateTeacher.this, "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadData(String s) {

        HashMap hp = new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("post",post);
        hp.put("image",s);

        reference.child(category).child(uniquekey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateTeacher.this, "Teacher Update Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateTeacher.this,UpdateFactluy.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageview.setImageBitmap(bitmap);
        }
    }



}