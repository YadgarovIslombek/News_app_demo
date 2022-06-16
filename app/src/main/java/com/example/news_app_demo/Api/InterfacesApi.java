package com.example.news_app_demo.Api;



import com.example.news_app_demo.Api.model.MainDataClass;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;

public interface InterfacesApi {

    @GET("everything")
    Call<MainDataClass> getData(
            @Query("q") String q,@Query("apiKey") String token);



    }
