package com.myapplication;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView mImageView;
    BroadcastReceiver broadcastReceiver;

    BottomNavigationView bottomNavigationView;
    FirebaseAuth auth;
    SupportMapFragment smf;
    FusedLocationProviderClient client;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        broadcastReceiver=new ConnectReceiver();
        registered();



//        Google MAp
//        smf=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.google_map);
//        client= LocationServices.getFusedLocationProviderClient(this);
//
//        Dexter.withContext(getApplicationContext())
//                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                        getmylocation();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                        permissionToken.continuePermissionRequest();
//                    }
//                }).check();

//        Loading Default Home Fragment in FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new HomeFragment())
                .commit();

        auth=FirebaseAuth.getInstance();


        bottomNavigationView=findViewById(R.id.bt);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home){
                replaceFrag(new HomeFragment());
            } else if (item.getItemId() == R.id.search) {
                replaceFrag(new searchFragment());
            } else if (item.getItemId() == R.id.addpost) {
                replaceFrag(new CatergoryFragment());
            } else if (item.getItemId() == R.id.sell) {
                replaceFrag(new SellFragment());
            } else if (item.getItemId()==R.id.profile) {
                replaceFrag(new ProfileFragment());
            }
            return true;
        });

//        Upload Section
        mImageView = findViewById(R.id.image_view);

    }

//    For Continuous Data Connection Checking
    protected void  registered(){
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
//    Replacing Fragments in Bottom Navigation
    public void replaceFrag(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
//    For Image Picker From Gallery
    public void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
       if(mImageView!=null) {
           Uri imageUri = data.getData();
           mImageView.setImageURI(imageUri);
       }
    }
}

    public void buttonchoose(View view) {
        openImagePicker();
    }


    public void dogs(View view) {
    Intent i=new Intent(getApplicationContext(), NewPost28.class);
    startActivity(i);

    }

    public void cats(View view) {
        Intent i=new Intent(getApplicationContext(), Cat_Category.class);
        startActivity(i);

    }

    public void birds(View view) {
        Intent i=new Intent(getApplicationContext(), Bird_Category.class);
        startActivity(i);
         }

    public void fishes(View view) {
        Intent i=new Intent(getApplicationContext(), Fish_Catergory.class);
        startActivity(i);

    }

    public void viewUploads(View view) {
        Intent i=new Intent(getApplicationContext(),ImagesActivty.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this).setMessage("Do you want to Exit ?").setCancelable(false)
                .setTitle(R.string.app_name)
                .setIcon(R.drawable.bgicon)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No" ,null).show();
    }

    public void logout(View view) {
        final SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        auth.signOut();
        Toast.makeText(getApplicationContext(), "Logout Sucessfully", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), Login.class));
    }
//    public void getmylocation() {
//        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Task<Location> task = client.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(final Location location) {
//                smf.getMapAsync(new OnMapReadyCallback() {
//                    @Override
//                    public void onMapReady(GoogleMap googleMap) {
//                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
//                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here...!!");
//
//                        googleMap.addMarker(markerOptions);
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
//                    }
//                });
//            }
//        });
//    }

}