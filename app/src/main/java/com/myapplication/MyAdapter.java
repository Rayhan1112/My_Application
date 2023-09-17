package com.myapplication;


import static android.content.Context.MODE_PRIVATE;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Model> mList;
    private Context context;

    public MyAdapter(Context context , ArrayList<Model> mList){

        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items , parent ,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getImageUri()).placeholder(R.drawable.placeholder).into(holder.imageView);
        holder.textView.setText("Name: "+mList.get(position).getName());
        holder.pbreed.setText("Breed: "+mList.get(position).getBreed());
        holder.pcolor.setText("Color: "+mList.get(position).getColor());
        holder.pweight.setText("Weight: "+mList.get(position).getWeight());
        holder.t.setText(mList.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView,pbreed,pcolor,pweight,t;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagetodisplay);
            textView=itemView.findViewById(R.id.petName);
            pbreed=itemView.findViewById(R.id.breed);
            pcolor=itemView.findViewById(R.id.color);
            pweight=itemView.findViewById(R.id.weight);
            progressBar=itemView.findViewById(R.id.progressBar_cyclic);
            t= (TextView) itemView.findViewById(R.id.nameofUser);







        }
    }
}
