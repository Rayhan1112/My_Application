package com.myapplication;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


public class Fish_Catergory extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button uploadImage;
    private ImageView addImage, preview;
    private StorageReference mreference = FirebaseStorage.getInstance().getReference();
    Uri mImageUri;
    ProgressDialog pd;
    EditText dname,dweight,dbreed,dcolor;
    private final int REQ = 1;
    private StorageReference mStorageRef;
    DatabaseReference root;
    String Uname,ID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_catergory);

        dname=findViewById(R.id.DogName);
        dweight=findViewById(R.id.weight);
        dbreed=findViewById(R.id.breed);
        dcolor=findViewById(R.id.color);

        final SharedPreferences sharedPreferences=getSharedPreferences("Data", MODE_PRIVATE);
        ID = sharedPreferences.getString("ID","");
        Uname=sharedPreferences.getString("UserName","");
//        ------------------------------------------------------------------------------------------------
        uploadImage = findViewById(R.id.uploadImageBtnGallery);
        addImage = findViewById(R.id.add_gallery_image);
        preview = findViewById(R.id.gallery_image_preview);
        mStorageRef= FirebaseStorage.getInstance().getReference().child(""+ID);
        root = FirebaseDatabase.getInstance().getReference(ID).child("User Posts");


        pd = new ProgressDialog(this);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageUri == null){
                    Toast.makeText(Fish_Catergory. this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                }else {
                    uploadData(mImageUri);
                }
            }
        });

    }
    //        _______________________________Send Data to Firebase_________________________
    private void uploadData(Uri uri) {
        String name=dname.getText().toString();
        String weight=dweight.getText().toString();
        String breed=dbreed.getText().toString();
        String color=dcolor.getText().toString();

        if(name.isEmpty()){
            dname.setError("Please Enter Name of Pet");
            Toast.makeText(this, "All field are required", Toast.LENGTH_SHORT).show();
        }else if (weight.isEmpty()){
            dweight.setError("Please Enter Weight");
            Toast.makeText(this, "All field are required", Toast.LENGTH_SHORT).show();

        }else if(breed.isEmpty()){
            dbreed.setError("Please Enter Breed");
            Toast.makeText(this, "All field are required", Toast.LENGTH_SHORT).show();

        }else if (color.isEmpty()){
            dcolor.setError("Please Enter Color");
            Toast.makeText(this, "All field are required", Toast.LENGTH_SHORT).show();
        }else{
            if(uri==null){
                Toast.makeText(this, "URI is NULL", Toast.LENGTH_SHORT).show();
            }

            final StorageReference fileRef = mreference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Model model=new Model(ID,Uname,uri.toString(),name,breed,color,weight);
                            String modelId = root.push().getKey();
                            root.child(modelId).setValue(model);
                            pd.dismiss();
                            Toast.makeText(Fish_Catergory.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            Clearall();

                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    pd.setMessage("Uploading Image Please Wait");;
                    pd.show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(Fish_Catergory.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    private void Clearall() {
        dname.setText("");
        dbreed.setText("");
        dweight.setText("");
        dcolor.setText("");
        preview.setImageDrawable(null);
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
//         ______________________________Fetch Pick from GAllery_________________________

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            preview.setImageURI(mImageUri);


        }
    }

}
