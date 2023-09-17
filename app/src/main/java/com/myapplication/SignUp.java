package com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    TextInputEditText username,email,password;
    String uname,uemail,upass;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


//        FireBase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();


        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
    }

    @SuppressLint("NotConstructor")
    public void SignUptoFirebase(View view) {

        uname = username.getText().toString();
        uemail = email.getText().toString();
        upass = password.getText().toString();

        if (uname.isEmpty()) {
            username.setError("Please Enter Username");
        } else if (uemail.isEmpty()) {
            email.setError("Please Enter Email");
        } else if (upass.isEmpty()) {
            password.setError("Please Enter Password");
        } else if (password.length() == 6 || password.length() < 6) {
            password.setError("Enter 6 Digit Password");
        } else {
            checkUser();
        }
        }

    private void CreateUser() {
        uname=username.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        auth.createUserWithEmailAndPassword(uemail, upass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                String key = databaseReference.push().getKey();
                SignUpData s = new SignUpData(uname, uemail, upass, key);
                databaseReference.child(key).child("User Data").setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        editor.putString("ID", key);
                        editor.putString("UserName", uname);
                        editor.commit();
                    }
                });
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(e.getMessage());

            }
        });




    }

    public void checkUser() {
        DatabaseReference d1=FirebaseDatabase.getInstance().getReference();
        d1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataSnapshot specificChildSnapshot = snapshot.child("User Data").child("username");
                    if (specificChildSnapshot.exists()) {
                        // Access the specific child's value
                        String  specificChildValue = (""+specificChildSnapshot.getValue());
                        uname = specificChildValue.toString();
                        if (username.getText().toString().equals(specificChildValue.toString())) {
                            Toast.makeText(SignUp.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                            System.out.println(uname);
                            break;
                        }else {
                            System.out.println("False....  ");
                            CreateUser();
                            break;

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Database error: " + databaseError.getMessage());
            }
        });
    }
}

