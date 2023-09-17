package com.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    TextInputEditText mail,pass,user;
    TextInputLayout t1,t2,t3;
    String email,upass,usernm;
    FirebaseAuth auth;
    public String type,key,uname;
    String finalUsername,finalKey;
    DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_login);


        final SharedPreferences sharedPreferences=getSharedPreferences("Data", MODE_PRIVATE);
        type = sharedPreferences.getString("ID","");
        if (type.isEmpty()) {
        } else {
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        auth=FirebaseAuth.getInstance();

        mail=findViewById(R.id.Lemail);
        pass=findViewById(R.id.Lpassword);
        user=findViewById(R.id.Luser);


        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
    }

    public void Logintofirebase(View view) {

        Auth();

    }

    private void Auth(){

        email = mail.getText().toString();
        upass = pass.getText().toString();
        usernm=user.getText().toString();
        try {
            if (usernm.isEmpty()){
                t3.setError("Please Enter Username");
            }else if (email.isEmpty()){
                t2.setError("Please Enter Email");
            } else if (upass.isEmpty()) {
                t3.setError("Please Enter Password");
            }
            else{
                SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                usernm=user.getText().toString();
                d1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            DataSnapshot specificChildSnapshot = snapshot.child("User Data").child("username");
                            if (specificChildSnapshot.exists()) {
                                // Access the specific child's value
                                String  specificChildValue = (""+specificChildSnapshot.getValue());
                                uname = specificChildValue.toString();
                                if (usernm.equals(specificChildValue.toString())) {
                                    finalUsername=uname;
                                    finalKey=snapshot.getKey();
                                    auth.signInWithEmailAndPassword(email, upass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    break;
                                }else {
                                    Toast.makeText(Login.this, "Invalid User Name", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }
                        editor.putString("ID", finalKey);
                        editor.putString("UserName",finalUsername);
                        editor.commit();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Database error: " + databaseError.getMessage());
                    }
                });



            }
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void signuphere(View view) {
        Intent i=new Intent(getApplicationContext(),SignUp.class);
        startActivity(i);
    }
}