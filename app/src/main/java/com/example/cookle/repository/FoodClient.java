package com.example.cookle.repository;

import com.example.cookle.network.FoodApi;
import com.example.cookle.pojo.Food;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodClient {

    private static final String BASE_URL = "http://recipesapi.herokuapp.com/api/";
    private FoodApi foodApi;
    private static FoodClient instance;

    private FoodClient() {
        this.foodApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(FoodApi.class);
    }

    public static FoodClient getInstance() {
        if (instance == null){
            return new FoodClient();
        }
        return instance;
    }

    public Observable<Food> getFood(String food){
        return foodApi.getFood(food);
    }
}
