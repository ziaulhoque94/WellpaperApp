package com.example.wellpaperapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class WallpaparActivity extends AppCompatActivity {

    private ImageView wallpaperIV;
    private Button setWallpaperBtn;
    String imgURL;
    WallpaperManager wallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapar);

        wallpaperIV=findViewById(R.id.idIVWallpaper);
        setWallpaperBtn=findViewById(R.id.idBtnSetWallpaper);
        imgURL=getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(imgURL).into(wallpaperIV);
        wallpaperManager=WallpaperManager.getInstance(getApplicationContext());

        setWallpaperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(WallpaparActivity.this).asBitmap().load(imgURL).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(getApplicationContext(), "Failed to load wallpaper", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource);
                        }catch (IOException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Failed to set Wallpaper", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                }).submit();
                FancyToast.makeText(WallpaparActivity.this,"Wallpaper to set home screen",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
            }
        });




    }
}