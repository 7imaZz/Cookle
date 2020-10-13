package com.example.cookle.network;

import com.example.cookle.pojo.Food;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface FoodApi {

    @GET("search?q=cookies")
    Observable<Food> getFood();
}
