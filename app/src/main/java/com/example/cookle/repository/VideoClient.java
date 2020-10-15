package com.example.cookle.repository;

import com.example.cookle.network.FoodApi;
import com.example.cookle.pojo.Food;
import com.example.cookle.pojo.Video;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoClient {

    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static final String API_KEY = "AIzaSyC7oez-3KSxPq60WDELfjsgLBxiuOI8YTg";
    private FoodApi foodApi;
    private static VideoClient instance;

    private VideoClient() {
        this.foodApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(FoodApi.class);
    }

    public static VideoClient getInstance() {
        if (instance == null){
            return new VideoClient();
        }
        return instance;
    }

    public Observable<Video> getVideo(String food){
        return foodApi.getVideo(food, API_KEY);
    }
}
