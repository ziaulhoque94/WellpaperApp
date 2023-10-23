package com.example.wellpaperapp.adaptar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wellpaperapp.R;
import com.example.wellpaperapp.modal.CatagoryRV_Modal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatagoryRV_Adaptar extends RecyclerView.Adapter<CatagoryRV_Adaptar.ViewHolder>{

    private ArrayList<CatagoryRV_Modal> arrayList;
    Context context;
    CatagoriClickInterface catagoriClickInterface;

    public CatagoryRV_Adaptar(Context context,ArrayList<CatagoryRV_Modal> arrayList, CatagoriClickInterface catagoriClickInterface) {
        this.arrayList = arrayList;
        this.context = context;
        this.catagoriClickInterface = catagoriClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.catagory_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CatagoryRV_Modal modal=arrayList.get(position);
        holder.catagoryTv.setText(modal.getCatagory());

        Glide.with(context).load(modal.getCatagoruIVUrl()).into(holder.catagoryIV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catagoriClickInterface.onCatagoryClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView catagoryIV;
        TextView catagoryTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catagoryIV=itemView.findViewById(R.id.idIVcatagory);
            catagoryTv=itemView.findViewById(R.id.idTVCatagory);
        }
    }


    public interface CatagoriClickInterface{
        void onCatagoryClick(int position);
    }
}
