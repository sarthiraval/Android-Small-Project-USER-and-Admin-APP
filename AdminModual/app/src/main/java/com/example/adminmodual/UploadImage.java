package com.example.adminmodual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadImage extends AppCompatActivity {

    Spinner imagecategory;
    CardView selectCategory;
    Button uploadimage;

    String downloadurl;
    DatabaseReference reference;
    StorageReference storageReference;
    ImageView galleryImageView;
    Bitmap bitmap;

    String category;
    private  final int REQ =1;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);


        reference = FirebaseDatabase.getInstance().getReference().child("gallery");
        storageReference = FirebaseStorage.getInstance().getReference().child("gallery");

        pd = new ProgressDialog(this);


        selectCategory = findViewById(R.id.addGalleryImage);
        imagecategory = findViewById(R.id.image_Category);
        uploadimage = findViewById(R.id.uploadImagebtn);
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap == null){
                    Toast.makeText(UploadImage.this, "Please Upload Image....", Toast.LENGTH_SHORT).show();
                }
                else if (category.equals("Select Category")){
                    Toast.makeText(UploadImage.this, "Please select Image Category", Toast.LENGTH_SHORT).show();
                }
                else {
                    pd.setMessage("Uploading......");
                    pd.show();
                    uploadImage();
                }
            }
        });
        galleryImageView = findViewById(R.id.galleryImageview);

        String[] items = new String[]{"Select Category","Convocation","Indepedance Day","Other Events"};
        imagecategory.setAdapter(new ArrayAdapter<String >(this, android.R.layout.simple_spinner_dropdown_item,items));


        imagecategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = imagecategory.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void uploadImage() {
        pd.setMessage("Uploading !!!!!");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage = baos.toByteArray();
        final StorageReference filepath= storageReference.child(finalImage+"jpg");
        final UploadTask uploadTask= filepath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(UploadImage.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    uploadData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(UploadImage.this, "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void uploadData() {

        reference = reference.child(category);
        final  String uniquekey = reference.push().getKey();
        reference.child(uniquekey).setValue(downloadurl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "Image uploaded!!!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UploadImage.this,MainActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "SomeThing is Wrong", Toast.LENGTH_SHORT).show();
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

            galleryImageView.setImageBitmap(bitmap);
        }
    }

}