package com.example.news_app_demo;

import static com.example.news_app_demo.util.Constant.API_TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.news_app_demo.Adapter.NewsAdapter;
import com.example.news_app_demo.Api.RetrofitClient;
import com.example.news_app_demo.model.MainDataClass;
import com.example.news_app_demo.model.ObjectDataClass;
import com.example.news_app_demo.model.Subject;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    NewsAdapter newsAdapter;
    private RecyclerView recyclerView,rv2;
   ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //Retrofit call
        Call<MainDataClass> call = RetrofitClient.getData(getApplicationContext()).getData("techcrunch.com", API_TOKEN);
        //recycler Vertical
        recyclerView = findViewById(R.id.recV);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //init data
        call.enqueue(new Callback<MainDataClass>() {
            @Override
            public void onResponse(Call<MainDataClass> call, Response<MainDataClass> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    List<ObjectDataClass> list = response.body().getArticles();
                    newsAdapter  = new NewsAdapter(list,MainActivity.this);
                    recyclerView.setAdapter(newsAdapter);
                   /* for (ObjectDataClass obj : list){
                        Log.d("author", obj.getAuthor());
                        Log.d("title", obj.getTitle());
                        Log.d("desc", obj.getDescription());
                        Log.d("url", obj.getUrl());
                        Log.d("image", obj.getUrlToImage());
                        Log.d("data", obj.getPublishedAt());
                        Log.d("content", obj.getContent());
                    }*/
                }else{
                    Log.d("response","faeilboldi");
                }
            }

            @Override
            public void onFailure(Call<MainDataClass> call, Throwable t) {
                Log.d("response","fail");
            }
        });

        //rv horizontal  Top
        rv2 = findViewById(R.id.recH);
        //init data
        ArrayList<Subject> subjectArrayList = new ArrayList<>();
        subjectArrayList.add(new Subject("English"));
        subjectArrayList.add(new Subject("Math"));
        subjectArrayList.add(new Subject("P.E"));
        subjectArrayList.add(new Subject("Science"));
        subjectArrayList.add(new Subject("Art"));
    }

}