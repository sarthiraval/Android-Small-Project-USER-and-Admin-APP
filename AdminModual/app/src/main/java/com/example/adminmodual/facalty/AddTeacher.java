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
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adminmodual.Teacher;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTeacher extends AppCompatActivity {

    ImageView addteacheimage;
    EditText addtrachename,addemail,addpost;
    Spinner addteacherrCategory;
    Button addteacheBtn;
    Bitmap bitmap= null;

    String category,name,emai,post,downloadurl="";
    DatabaseReference reference,dbref;
    StorageReference storageReference;
    private  final int REQ =1;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        reference = FirebaseDatabase.getInstance().getReference().child("Teacher");
        storageReference = FirebaseStorage.getInstance().getReference().child("Teacher");
        pd = new ProgressDialog(this);
        addteacheimage = findViewById(R.id.addtecahetimageview);
        addtrachename = findViewById(R.id.addTechaerName);
        addemail = findViewById(R.id.addTechaeEmail);
        addpost = findViewById(R.id.addTechaerpost);
        addteacherrCategory = findViewById(R.id.addteacheCategory);
        addteacheBtn = findViewById(R.id.addTecherntn);
        addteacheimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        addteacheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chekvalidastion();
            }
        });
        String[] items = new String[]{"Select Category","Account","State","English","Eco","BA","Gujarati"};
        addteacherrCategory.setAdapter(new ArrayAdapter<String >(this, android.R.layout.simple_spinner_dropdown_item,items));
        addteacherrCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addteacherrCategory.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void chekvalidastion() {
        name = addtrachename.getText().toString().trim();
        emai = addemail.getText().toString().trim();
        post = addpost.getText().toString().trim();
        if(name.isEmpty() ){
            addtrachename.setError("Empty");
            addtrachename.requestFocus();
        }
        else if(emai.isEmpty()){
            addemail.setError("Empty");
            addemail.requestFocus();
        }else if(post.isEmpty()){
            addpost.setError("Empty");
            addpost.requestFocus();
        }
        else if(category.equals("Select Category")){
            Toast.makeText(this, "Please provide Teacher Category", Toast.LENGTH_SHORT).show();
        }
        else if(bitmap == null){
            Toast.makeText(this, "Please provide Teacher Image", Toast.LENGTH_SHORT).show();
        }else{
            pd.setMessage("Uploading !!!!!");
            pd.show();
            ineertsimage();
        }
    }
    private void insertdate() {
        dbref = reference.child(category);
        final  String uniquekey = dbref.push().getKey();
        Teacher teacher = new Teacher(name,emai,post,downloadurl,uniquekey);
        dbref.child(uniquekey).setValue(teacher).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddTeacher.this, "Teacher  Added!!!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddTeacher.this, UpdateFactluy.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddTeacher.this, "SomeThing is Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void ineertsimage() {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
            byte[] finalImage = baos.toByteArray();
            final StorageReference filepath= storageReference.child("Teacher").child(finalImage+"jpg");
            final UploadTask uploadTask= filepath.putBytes(finalImage);
            uploadTask.addOnCompleteListener(AddTeacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                        insertdate();
                                    }
                                });
                            }
                        });
                    }else {
                        pd.dismiss();
                        Toast.makeText(AddTeacher.this, "Something is wrong", Toast.LENGTH_SHORT).show();
                    }
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
            addteacheimage.setImageBitmap(bitmap);
        }
    }


}