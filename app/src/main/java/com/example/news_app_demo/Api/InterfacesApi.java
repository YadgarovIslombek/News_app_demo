package com.example.news_app_demo.Api;

import com.example.news_app_demo.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;

public interface InterfacesApi {

    @GET("top-headlines")
    Call<NewsModel> getData(
            @Query("country") String country,
            @Query("apiKey") String token);


   @GET("everything")
   Call<NewsModel> getNewsSearch(

           @Query("q") String keyword,
           @Query("language") String language,
           @Query("sortBy") String sortBy,
           @Query("apiKey") String apiKey

   );

}
