package com.example.cookle.network;

import com.example.cookle.pojo.Food;
import com.example.cookle.pojo.Video;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodApi {

    @GET("search")
    Observable<Food> getFood(@Query("q")String food);

    @GET("search")
    Observable<Video> getVideo(@Query("q")String food, @Query("key")String api);
}
