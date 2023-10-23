package com.example.wellpaperapp.adaptar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wellpaperapp.R;
import com.example.wellpaperapp.WallpaparActivity;
import com.example.wellpaperapp.modal.CatagoryRV_Modal;

import java.util.ArrayList;

public class WallpaperRV_Adapter extends RecyclerView.Adapter<WallpaperRV_Adapter.ViewHolder>{
        ArrayList<String> wallpaparRVArraylist;
        Context context;

    public WallpaperRV_Adapter(ArrayList<String> wallpaparRVArraylist, Context context) {
        this.wallpaparRVArraylist = wallpaparRVArraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.wallpaper_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(wallpaparRVArraylist.get(position)).into(holder.wallpaperIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, WallpaparActivity.class);
                intent.putExtra("imgUrl",wallpaparRVArraylist.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaparRVArraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView wallpaperIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaperIV=itemView.findViewById(R.id.idIVWallpaper);
        }
    }
}
