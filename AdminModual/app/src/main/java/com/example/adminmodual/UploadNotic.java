package com.example.adminmodual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class UploadNotic extends AppCompatActivity {

    CardView addImage;
    ImageView noticeImageView;
    private  final int REQ =1;
    Bitmap bitmap;
    EditText noticeTitle;
    Button uploadnoticeButton;
    String downloaduri = "";
    DatabaseReference reference,dbref;
    StorageReference storageReference;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notic);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);

        noticeTitle= findViewById(R.id.noticetitle);
        uploadnoticeButton = findViewById(R.id.uploadnoticebtn);
        noticeImageView = findViewById(R.id.noticeImageview);

        uploadnoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noticeTitle.getText().toString().isEmpty()){
                    noticeTitle.setError("Empty");
                    noticeTitle.requestFocus();
                }else if(bitmap==null){
                    uploadData();
                }
                else
                {
                    uploadImage();
                }
            }
        });

        addImage = findViewById(R.id.addimage);
        addImage.setOnClickListener(new View.OnClickListener() {
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
        final StorageReference filepath= storageReference.child("Notice").child(finalImage+"jpg");
        final UploadTask uploadTask= filepath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(UploadNotic.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {
                               downloaduri = String.valueOf(uri);
                               uploadData();
                           }
                       });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(UploadNotic.this, "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadData() {

        dbref = reference.child("Notice");
        final  String uniquekey = dbref.push().getKey();
        String title = noticeTitle.getText().toString();
        Calendar callforDate = Calendar.getInstance();
        SimpleDateFormat cureentDate = new SimpleDateFormat("dd-MM-yy");
        String date = cureentDate.format(callforDate.getTime());

        Calendar callforTIme =Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(callforTIme.getTime());

        Notice notice = new Notice(title,downloaduri,date,time,uniquekey);
        dbref.child(uniquekey).setValue(notice).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UploadNotic.this, "Notice uploaded!!!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UploadNotic.this,MainActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotic.this, "SomeThing is Wrong", Toast.LENGTH_SHORT).show();
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

            noticeImageView.setImageBitmap(bitmap);
        }
    }
}