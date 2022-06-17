package com.example.news_app_demo;

import static com.example.news_app_demo.util.Constant.API_TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.news_app_demo.Adapter.NewsAdapter;
import com.example.news_app_demo.Adapter.SubjectAdapter;
import com.example.news_app_demo.Api.RetrofitClient;
import com.example.news_app_demo.model.NewsModel;
import com.example.news_app_demo.model.ObjectDataClass;
import com.example.news_app_demo.model.Subject;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView,rv2;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ObjectDataClass> objData = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private TextView topHeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        topHeadline = findViewById(R.id.topheadelines);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh("");

       //Retrofit call
     //   Call<MainDataClass> call = RetrofitClient.getData(getApplicationContext()).getData("techcrunch.com", API_TOKEN);
        //recycler Vertical
        /*recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());*/

        //init data
     /*   call.enqueue(new Callback<MainDataClass>() {
            @Override
            public void onResponse(Call<MainDataClass> call, Response<MainDataClass> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    List<ObjectDataClass> list = response.body().getArticles();
                    newsAdapter  = new NewsAdapter(list,MainActivity.this);
                    recyclerView.setAdapter(newsAdapter);
                   *//* for (ObjectDataClass obj : list){
                        Log.d("author", obj.getAuthor());
                        Log.d("title", obj.getTitle());
                        Log.d("desc", obj.getDescription());
                        Log.d("url", obj.getUrl());
                        Log.d("image", obj.getUrlToImage());
                        Log.d("data", obj.getPublishedAt());
                        Log.d("content", obj.getContent());
                    }*//*
                }else{
                    Log.d("response","faeilboldi");
                }
            }

            @Override
            public void onFailure(Call<MainDataClass> call, Throwable t) {
                Log.d("response","fail");
            }
        });*/

        //rv horizontal  Top
      /*  rv2 = findViewById(R.id.recH);
        //init data
        ArrayList<Subject> subjectArrayList = new ArrayList<>();
        subjectArrayList.add(new Subject("English"));
        subjectArrayList.add(new Subject("Math"));
        subjectArrayList.add(new Subject("P.E"));
        subjectArrayList.add(new Subject("Science"));
        subjectArrayList.add(new Subject("Art"));
        subjectArrayList.add(new Subject("Art"));
        subjectArrayList.add(new Subject("Art"));
        subjectArrayList.add(new Subject("Art"));
        subjectArrayList.add(new Subject("Art"));
        subjectArrayList.add(new Subject("Art"));

        SubjectAdapter adapter = new SubjectAdapter(subjectArrayList,MainActivity.this);
        rv2.setAdapter(adapter);
// Set LayoutManager
        rv2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));*/
    }
    public void LoadJson(final String keyword){

       // errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);



        String country = Util.getCountry();
        String language = Util.getLanguage();

        Call<NewsModel> call;

        if (keyword.length() > 0 ){
            call = RetrofitClient.getData(MainActivity.this).getNewsSearch(keyword, language, "publishedAt", API_TOKEN);
        } else {
            call = RetrofitClient.getData(MainActivity.this).getData(country, API_TOKEN);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){

                    if (!objData.isEmpty()){
                        objData.clear();
                    }

                    objData = response.body().getArticles();
                    newsAdapter = new NewsAdapter(objData, MainActivity.this);
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();

                    //initListener();

                    topHeadline.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);


                } else {

                    topHeadline.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }

                   /* showErrorMessage(
                            R.drawable.no_result,
                            "No Result",
                            "Please Try Again!\n"+
                                    errorCode);*/

                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                topHeadline.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                /*showErrorMessage(
                        R.drawable.oops,
                        "Oops..",
                        "Network failure, Please Try Again\n"+
                                t.toString());*/
            }
        });

    }




    @Override
    public void onRefresh() {
        LoadJson("");
    }
    private void onLoadingSwipeRefresh(final String keyword){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson(keyword);
                    }
                }
        );

    }

}