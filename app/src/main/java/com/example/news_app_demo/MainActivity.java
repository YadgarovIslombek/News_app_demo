package com.example.news_app_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.news_app_demo.Api.InterfacesApi;
import com.example.news_app_demo.Api.RetrofitClient;
import com.example.news_app_demo.Api.model.MainDataClass;
import com.example.news_app_demo.Api.model.ObjectDataClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Call<MainDataClass> call = RetrofitClient.getData(getApplicationContext()).getData("keyword","3fb26fce17bc49d0a5d174e333aa0a91");

        call.enqueue(new Callback<MainDataClass>() {
            @Override
            public void onResponse(Call<MainDataClass> call, Response<MainDataClass> response) {
                if(response.isSuccessful()){
                    List<ObjectDataClass> list = response.body().getArticles();
                    for (ObjectDataClass obj : list){
                        Log.d("author", obj.getAuthor());
                        Log.d("title", obj.getTitle());
                        Log.d("desc", obj.getDescription());
                        Log.d("url", obj.getUrl());
                        Log.d("image", obj.getUrlToImage());
                        Log.d("data", obj.getPublishedAt());
                        Log.d("content", obj.getContent());
                    }
                }else{
                    Log.d("response","faeilboldi");
                }
            }

            @Override
            public void onFailure(Call<MainDataClass> call, Throwable t) {
                Log.d("response","fail");
            }
        });



        /*Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfacesApi interfacesApi = retrofit.create(InterfacesApi.class);
       Call<MainDataClass>call = interfacesApi.getData1("keyword","3fb26fce17bc49d0a5d174e333aa0a91");
        call.enqueue(new Callback<MainDataClass>() {
            @Override
            public void onResponse(Call<MainDataClass> call, Response<MainDataClass> response) {
                if(response.isSuccessful()){
                List<ObjectDataClass> list = response.body().getArticles();
                for (ObjectDataClass obj : list){
                    Log.d("author", obj.getAuthor());
                    Log.d("title", obj.getTitle());
                    Log.d("desc", obj.getDescription());
                    Log.d("url", obj.getUrl());
                    Log.d("image", obj.getUrlToImage());
                    Log.d("data", obj.getPublishedAt());
                    Log.d("content", obj.getContent());
                }
                }else{
                    Log.d("response","faeilboldi");
                }
            }

            @Override
            public void onFailure(Call<MainDataClass> call, Throwable t) {
                Log.d("response","fail");
            }
        });

    }*/
    }
}