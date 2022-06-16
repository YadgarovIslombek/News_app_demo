package com.example.news_app_demo.Api;



import com.example.news_app_demo.Api.model.MainDataClass;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface InterfacesApi {

    @GET("everything")
    Call<MainDataClass> getData(
            @Query("domains") String q,@Query("apiKey") String token);

   /* @GET("application/json")
    Call<CategoryResult> getProductById
            (@Query("groupId") int productGroupId);
*/

    }
