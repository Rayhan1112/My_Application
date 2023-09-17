package com.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Model> list;
    private MyAdapter adapter;
    ProgressBar progressBar;



    private DatabaseReference root,d;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        final SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("Data", MODE_PRIVATE);
        String ID = sharedPreferences.getString("ID","");

        progressBar=view.findViewById(R.id.progressBar_cyclic);
        root = FirebaseDatabase.getInstance().getReference();


        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        d=FirebaseDatabase.getInstance().getReference();

        list = new ArrayList<>();





        root.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                          Model model=new Model();
                          String key = dataSnapshot.getKey();
                          DatabaseReference username = root.child(key).child("User Data").child("username");
                          username.addValueEventListener(new ValueEventListener() {
                              @Override
                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                  model.setUserName(snapshot.getValue(String.class));
                                  model.setUserID(key);
                              }
                              @Override
                              public void onCancelled(@NonNull DatabaseError error) {

                              }
                          });
                          DatabaseReference singleuser = root.child(key).child("User Posts");
                          singleuser.addValueEventListener(new ValueEventListener() {
                              @Override
                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                  for(DataSnapshot dataSnapshot1:snapshot.getChildren()) {
                                      Model m = dataSnapshot1.getValue(Model.class);
                                      list.add(m);
                                  }
                                  adapter = new MyAdapter(getContext(),list);
                                  recyclerView.setAdapter(adapter);
                              }

                              @Override
                              public void onCancelled(@NonNull DatabaseError error) {
                                  Toast.makeText(getContext(), "No Data TO Show", Toast.LENGTH_SHORT).show();
                              }
                          });
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {
                      Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                  }
              });
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}