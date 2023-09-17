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

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.PViewHolder> {

    private ArrayList<PModel> mList;
    private Context context;

    public ProfileAdapter(Context context , ArrayList<PModel> mList){

        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public PViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid , parent ,false);
        return new PViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getImageUri()).into(holder.imageView);
        holder.textView.setText("Name: "+mList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class PViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public PViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            textView=itemView.findViewById(R.id.item_text);
        }
    }
}
