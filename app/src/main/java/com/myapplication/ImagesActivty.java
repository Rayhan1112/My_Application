package com.myapplication;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import static kotlin.jvm.internal.Reflection.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImagesActivty extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Model> list;

    private MyAdapter adapter;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");



    StorageReference storageReference=FirebaseStorage.getInstance().getReference("uploads/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_activty);

//        ImageView imageView=findViewById(R.id.mImageView);


//        mRecyclerView = findViewById(R.id.recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this); // Change as needed
//        recyclerView.setLayoutManager(layoutManager);
//
//        List<String> yourDataList = new ArrayList<>(); // Replace with your data
//        ImageAdapter adapter = new ImageAdapter(yourDataList);
//        recyclerView.setAdapter(adapter);
//        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

//        List<Upload> mUploads = new ArrayList<>();
//        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("image");


//        storageReference.listAll()
//                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                    @Override
//                    public void onSuccess(ListResult listResult) {
//                        for (StorageReference item : listResult.getItems()) {
//
//                            // You can get the download URL for each image
//                            int count=listResult.getItems().size();
//                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
////                                    String imageUrl = uri.toString();
////                                  imageView.setImageURI(null);
////                                    Toast.makeText(ImagesActivty.this, ""+ count, Toast.LENGTH_SHORT).show();
//                                    System.out.println(""+uri);
//////
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception exception) {
//                                    // Handle any errors here
//
//                                }
//                            });
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Handle any errors here
//                    }
//                });



//        mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                try {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        Upload upload = postSnapshot.getValue(Upload.class);
//                        mUploads.add(upload);
//                    }
//                    mAdapter = new ImageAdapter(ImagesActivty.this, mUploads);
//                    mRecyclerView.setAdapter(mAdapter);
//                }catch (Exception e){
//                    Toast.makeText(ImagesActivty.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(ImagesActivty.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String image =snapshot.getValue().toString();
//
//                Toast.makeText(ImagesActivty.this, ""+image, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ImagesActivty.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        storageReference.child("signup1.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                if(uri==null){
//                    Toast.makeText(ImagesActivty.this, "Empty URl", Toast.LENGTH_SHORT).show();
//                }
//                try {
////                    ImageView imageView=findViewById(R.id.mImageView);
//                    imageView.setImageURI(null);
//                    Glide.with(getApplicationContext()).load(uri).into(imageView);
//                }catch (Exception e)
//                {
//                    Toast.makeText(ImagesActivty.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });



//        // we will get the default FirebaseDatabase instance
//        FirebaseDatabase firebaseDatabase
//                = FirebaseDatabase.getInstance();
//
//        // we will get a DatabaseReference for the database
//        // root node
//        DatabaseReference databaseReference
//                = firebaseDatabase.getReference();
//
//        // Here "image" is the child node value we are
//        // getting child node data in the getImage variable
//        DatabaseReference getImage
//                = databaseReference.child("image");
//
//        // Adding listener for a single change
//        // in the data at this location.
//        // this listener will triggered once
//        // with the value of the data at the location
//        getImage.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(
//                            @NonNull DataSnapshot dataSnapshot)
//                    {
//                        // getting a DataSnapshot for the
//                        // location at the specified relative
//                        // path and getting in the link variable
//                        String link = dataSnapshot.getValue(
//                                String.class);
//
//                        // loading that data into rImage
//                        // variable which is ImageView
////                        Picasso.get().load(link).into(rImageView);
//                        rImageView.setImageURI(Uri.parse(link));
//                        Toast.makeText(ImagesActivty.this, ""+Uri.parse(link), Toast.LENGTH_SHORT).show();
//                    }
//
//                    // this will called when any problem
//                    // occurs in getting data
//                    @Override
//                    public void onCancelled(
//                            @NonNull DatabaseError databaseError)
//                    {
//                        // we are showing that error message in
//                        // toast
//                        Toast
//                                .makeText(ImagesActivty.this,
//                                        "Error Loading Image",
//                                        Toast.LENGTH_SHORT)
//                                .show();
//                    }
//                });
//    }
//        imagelist=new ArrayList<>();
//        recyclerView=findViewById(R.id.recyclerview);
//        adapter=new ImageAdapter(imagelist,this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(null));
//        progressBar=findViewById(R.id.progress);
//        progressBar.setVisibility(View.VISIBLE);
//        StorageReference listRef = FirebaseStorage.getInstance().getReference().child("uploads");
//        listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//            @Override
//            public void onSuccess(ListResult listResult) {
//                for(StorageReference file:listResult.getItems()){
//                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            // adding the url in the arraylist
//                            imagelist.add(uri.toString());
//                            Log.e("Itemvalue",uri.toString());
//                        }
//                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            recyclerView.setAdapter(adapter);
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            }
//        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MyAdapter(this , list);
        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
