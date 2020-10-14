package com.example.cookle.network;

import com.example.cookle.pojo.Food;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodApi {

    @GET("search")
    Observable<Food> getFood(@Query("q")String food);
}
