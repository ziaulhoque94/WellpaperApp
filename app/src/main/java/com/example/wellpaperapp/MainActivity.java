package com.example.wellpaperapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wellpaperapp.adaptar.CatagoryRV_Adaptar;
import com.example.wellpaperapp.adaptar.WallpaperRV_Adapter;
import com.example.wellpaperapp.modal.CatagoryRV_Modal;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CatagoryRV_Adaptar.CatagoriClickInterface{

    private EditText searchEdt;
    private ImageView searchIV;
    private RecyclerView catagoryRV,wallpaperRV;
    private ProgressBar loading;


    ArrayList<String> wallpaperArraylist;
    ArrayList<CatagoryRV_Modal> catagoryRV_modalArrayList;

    CatagoryRV_Adaptar catagoryRV_adaptar;
    WallpaperRV_Adapter wallpaperRV_adapter;

//    563492ad6f917000010000010e7a55d74fdf4691a2518ea0831cf94a


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEdt=findViewById(R.id.editSearch);
        searchIV=findViewById(R.id.idIVSearch);
        catagoryRV=findViewById(R.id.idRVCatagory);
        wallpaperRV=findViewById(R.id.idRVWallpaper);
        loading=findViewById(R.id.idPBLoding);

        catagoryRV_modalArrayList=new ArrayList<>();
        wallpaperArraylist=new ArrayList<>();

        LinearLayoutManager manager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        catagoryRV.setLayoutManager(manager);

        catagoryRV_adaptar=new CatagoryRV_Adaptar(this,catagoryRV_modalArrayList,this);
        catagoryRV.setAdapter(catagoryRV_adaptar);


//        wallpaper code..
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        wallpaperRV.setLayoutManager(gridLayoutManager);

        wallpaperRV_adapter=new WallpaperRV_Adapter(wallpaperArraylist,this);
        wallpaperRV.setAdapter(wallpaperRV_adapter);

        getCatagorys();
        getWallPapers();

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchStr=searchEdt.getText().toString();
                if (searchStr.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter your search query", Toast.LENGTH_SHORT).show();
                }else{
                    getWallpaperByCatagory(searchStr);
                }
            }
        });

    }
   private void getWallpaperByCatagory(String catagory) {
        wallpaperArraylist.clear();
        loading.setVisibility(View.VISIBLE);
        String url="https://api.pexels.com/v1/search?query="+catagory+"&per_page=30&page=1";


        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                try {
                    JSONArray photosArray = new JSONArray("photos");
                    for (int i = 0; i < photosArray.length(); i++) {
                        JSONObject photosObject = photosArray.getJSONObject(i);
                        String imgUrl=photosObject.getJSONObject("src").getString("portrait");
                       // String imgUrl= photosObject.getJSONObject("src").get("portrait");
                        wallpaperArraylist.add(imgUrl);
                       // System.out.print("Woriking"+name);
                    }
                    wallpaperRV_adapter.notifyDataSetChanged();
                }catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed to load wallpaper", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header=new HashMap<>();
                header.put("Authorization","563492ad6f917000010000010e7a55d74fdf4691a2518ea0831cf94a");
                return header;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }


//    get wallpaper method
    private void getWallPapers(){
        wallpaperArraylist.clear();
        loading.setVisibility(View.VISIBLE);
        String url="https://api.pexels.com/v1/curated?per_page=30&page=1";


        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                JSONArray photosArray=null;
                try {
                    photosArray=new JSONArray("photos");
                    for (int i = 0; i < photosArray.length(); i++) {
                        JSONObject photosObject=photosArray.getJSONObject(i);
                        String imgUrl=photosObject.getJSONObject("src").getString("portrait");
                        wallpaperArraylist.add(imgUrl);
                    }
                    wallpaperRV_adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "fail to load wallpaper", Toast.LENGTH_SHORT).show();
            }
        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "563492ad6f917000010000010e7a55d74fdf4691a2518ea0831cf94a");
                return header;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    //catagory method
    @SuppressLint("NotifyDataSetChanged")
    private void getCatagorys() {
        catagoryRV_modalArrayList.add(new CatagoryRV_Modal("Technology","https://www.google.com/imgres?imgurl=https%3A%2F%2Ftranscosmos.co.uk%2Fwp-content%2Fuploads%2F2015%2F10%2Ftechnology-customer-support1.jpg&imgrefurl=https%3A%2F%2Ftranscosmos.co.uk%2Fblog%2Fjust-how-important-technology-is-to-customer-service-in-todays-time%2F&tbnid=lO7YexfnZJg-6M&vet=12ahUKEwjylJOukK78AhUpk9gFHfYkCJgQMygDegUIARDCAQ..i&docid=d87fbs0hBa9hLM&w=3796&h=2780&q=technology&ved=2ahUKEwjylJOukK78AhUpk9gFHfYkCJgQMygDegUIARDCAQ"));
        catagoryRV_modalArrayList.add(new CatagoryRV_Modal("Programming","https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.codingem.com%2Fwhat-is-programming%2F&psig=AOvVaw3z-nOQ0kQJ_J7oMo7FMo_A&ust=1672929091527000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCPCFudOQrvwCFQAAAAAdAAAAABAE"));
        catagoryRV_modalArrayList.add(new CatagoryRV_Modal("Nature","https://www.google.com/imgres?imgurl=https%3A%2F%2Fthumbs.dreamstime.com%2Fb%2Fbeautiful-rain-forest-ang-ka-nature-trail-doi-inthanon-national-park-thailand-36703721.jpg&imgrefurl=https%3A%2F%2Fwww.dreamstime.com%2Fphotos-images%2Fnature.html&tbnid=3du_EqKvbNmtvM&vet=12ahUKEwjnhPipka78AhX5BbcAHZHrCfsQMygFegUIARC-AQ..i&docid=bH1YFPQkm85agM&w=800&h=533&q=Nature&ved=2ahUKEwjnhPipka78AhX5BbcAHZHrCfsQMygFegUIARC-AQ"));
        catagoryRV_modalArrayList.add(new CatagoryRV_Modal("Travel","https://www.google.com/imgres?imgurl=https%3A%2F%2Fdynamic.com.bd%2Fpublic%2Fassets%2Fimg%2Fhome.jpg&imgrefurl=https%3A%2F%2Fdynamic.com.bd%2F&tbnid=_2vil4zh-6trtM&vet=12ahUKEwjf2eu4ka78AhU0i9gFHSA9BB4QMygBegUIARC3AQ..i&docid=6JaeV_ZPdar0JM&w=600&h=400&q=Travel&ved=2ahUKEwjf2eu4ka78AhU0i9gFHSA9BB4QMygBegUIARC3AQ"));
        catagoryRV_modalArrayList.add(new CatagoryRV_Modal("Architecture","https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.pinimg.com%2F736x%2Fa3%2Fed%2F71%2Fa3ed71def3bc46b89354b0f68f660651.jpg&imgrefurl=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F718324209304651133%2F&tbnid=fLG4sgbcObb6aM&vet=12ahUKEwis8bTOka78AhX87nMBHeJWCFIQMygDegUIARDBAQ..i&docid=liRuxL0wuIH8UM&w=736&h=635&q=architecture&ved=2ahUKEwis8bTOka78AhX87nMBHeJWCFIQMygDegUIARDBAQ"));
        catagoryRV_modalArrayList.add(new CatagoryRV_Modal("Arts","https://www.google.com/imgres?imgurl=https%3A%2F%2Fimages.theconversation.com%2Ffiles%2F339504%2Foriginal%2Ffile-20200603-130907-asv1yo.jpg%3Fixlib%3Drb-1.1.0%26q%3D45%26auto%3Dformat%26w%3D1200%26h%3D1200.0%26fit%3Dcrop&imgrefurl=https%3A%2F%2Ftheconversation.com%2Fbrain-research-shows-the-arts-promote-mental-health-136668&tbnid=Xzs3sasbYYKjEM&vet=12ahUKEwiR447vka78AhUbjNgFHbryDQYQMygBegUIARC3AQ..i&docid=H-OeKWXldC6RAM&w=1200&h=1200&q=Arts&ved=2ahUKEwiR447vka78AhUbjNgFHbryDQYQMygBegUIARC3AQ"));
        catagoryRV_modalArrayList.add(new CatagoryRV_Modal("Music","https://www.google.com/imgres?imgurl=https%3A%2F%2Fvariety.com%2Fwp-content%2Fuploads%2F2022%2F07%2FMusic-Streaming-Wars.jpg&imgrefurl=https%3A%2F%2Fvariety.com%2F2022%2Fmusic%2Fnews%2Fnew-songs-100000-being-released-every-day-dsps-1235395788%2F&tbnid=Gcf4UVl-IdmYMM&vet=12ahUKEwjDkru2kq78AhWqKbcAHUpbDx4QMygBegUIARC5AQ..i&docid=vn3ECZe7ntNyHM&w=1920&h=1080&q=Music&ved=2ahUKEwjDkru2kq78AhWqKbcAHUpbDx4QMygBegUIARC5AQ"));
        catagoryRV_modalArrayList.add(new CatagoryRV_Modal("Abstract","https://www.google.com/imgres?imgurl=https%3A%2F%2Fimages.pexels.com%2Fphotos%2F5186869%2Fpexels-photo-5186869.jpeg%3Fcs%3Dsrgb%26dl%3Dpexels-fiona-art-5186869.jpg%26fm%3Djpg&imgrefurl=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Fabstract%2F&tbnid=-t9Fi9FYIQlOBM&vet=12ahUKEwjM5O3Mkq78AhVHhNgFHbwLBogQMygAegUIARC9AQ..i&docid=eHNjO9eXROmJNM&w=2910&h=1636&itg=1&q=abstract&ved=2ahUKEwjM5O3Mkq78AhVHhNgFHbwLBogQMygAegUIARC9AQ"));
        catagoryRV_modalArrayList.add(new CatagoryRV_Modal("Cars","https://www.google.com/imgres?imgurl=https%3A%2F%2Fcars.usnews.com%2Fimages%2Farticle%2F202012%2F128775%2F1_2021_bugatti_chiron_super_sport.jpg&imgrefurl=https%3A%2F%2Fcars.usnews.com%2Fcars-trucks%2Fadvice%2Fmost-expensive-cars&tbnid=aJdTBoGQ9B5PHM&vet=12ahUKEwjl_Ijjkq78AhWnjtgFHe8PC54QMygMegUIARDXAQ..i&docid=ciP2dZezEwYB4M&w=3455&h=2304&q=Cars&ved=2ahUKEwjl_Ijjkq78AhWnjtgFHe8PC54QMygMegUIARDXAQ"));
        catagoryRV_modalArrayList.add(new CatagoryRV_Modal("Flower","https://www.google.com/imgres?imgurl=https%3A%2F%2Fcdn.britannica.com%2F84%2F73184-004-E5A450B5%2FSunflower-field-Fargo-North-Dakota.jpg&imgrefurl=https%3A%2F%2Fwww.britannica.com%2Fscience%2Fflower&tbnid=GGrA_FqXnfse-M&vet=12ahUKEwix1vT0kq78AhWULrcAHeb7BegQMygGegUIARC-AQ..i&docid=Zih9vqAzJOmeTM&w=550&h=340&q=Flower&ved=2ahUKEwix1vT0kq78AhWULrcAHeb7BegQMygGegUIARC-AQ"));
        catagoryRV_adaptar.notifyDataSetChanged();
    }


    @Override
    public void onCatagoryClick(int position) {
        String catagory=catagoryRV_modalArrayList.get(position).getCatagory();
        getWallpaperByCatagory(catagory);
    }
}