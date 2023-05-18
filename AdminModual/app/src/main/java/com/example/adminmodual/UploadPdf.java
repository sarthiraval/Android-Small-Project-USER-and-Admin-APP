package com.example.adminmodual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.File;
import java.util.HashMap;


public class UploadPdf extends AppCompatActivity {

    CardView addpdf;
    private  final int REQ =1;
   private String pdfName,title;
   private EditText pdfTitle;
    Button uploadpdfButton;
    DatabaseReference databaseReference;
    Uri pdfData;
    StorageReference storageReference;
    private ProgressDialog pd;
    TextView pdftextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        pdftextview = findViewById(R.id.pdftextview);

        pd = new ProgressDialog(this);
        addpdf = findViewById(R.id.addpdf);
        pdfTitle= findViewById(R.id.pdftitle);
        uploadpdfButton = findViewById(R.id.uploadpdfebtn);
        uploadpdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = pdfTitle.getText().toString();
                if (title.isEmpty()){
                    pdfTitle.setError("Empty");
                    pdfTitle.requestFocus();
                }else if (pdfData == null){
                    Toast.makeText(UploadPdf.this, "Please upload pdf", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadPdf();
                }
            }
        });
        addpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpdf();
            }
        });



    }

    private void uploadPdf() {
        pd.setTitle("Please Wait.....");
        pd.setMessage("Uploading pdf.....");
        pd.show();
        StorageReference reference = storageReference.child("pdf/"+pdfName+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri>  uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask .isComplete());
                        Uri uri = uriTask.getResult();
                        uploadData(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(UploadPdf.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadData(String valueOf) {

        String uniquekey = databaseReference.child("pdf").push().getKey();
        HashMap data = new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",valueOf);
        databaseReference.child("pdf").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadPdf.this, "Pdf uploaded successfully", Toast.LENGTH_SHORT).show();
                pdfTitle.setText("");
                Intent intent = new Intent(UploadPdf.this,MainActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPdf.this, "Failed to Upload pdf", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openpdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf Title"),REQ);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK){

            pdfData = data.getData();

            if (pdfData.toString().startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = UploadPdf.this.getContentResolver().query(pdfData,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else if(pdfData.toString().startsWith("file://")){
                pdfName = new File(pdfData.toString()).getName();
            }
            pdftextview.setText(pdfName);
        }
    }

}