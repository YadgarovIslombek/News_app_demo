package com.example.news_app_demo.Api;


import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "https://newsapi.org/v2/";
    private static RetrofitClient mInstance;
    private static Retrofit retrofit;
    private static Retrofit getRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.baseUrl("http://api.larntech.net/")
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


    }

    private static Interceptor provideLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
    private static OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .addNetworkInterceptor(provideLoggingInterceptor())
                .build();
    }
    public static InterfacesApi getData(Context applicationContext){
        return getRetrofit().create(InterfacesApi.class);
    }
}
