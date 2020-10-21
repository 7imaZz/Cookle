package com.example.cookle.repository;

import android.content.Context;

import com.example.cookle.RoomDB.FoodDB;
import com.example.cookle.network.FoodApi;
import com.example.cookle.pojo.Food;
import com.example.cookle.pojo.Ingredient;
import com.example.cookle.pojo.Recipe;
import com.example.cookle.pojo.RecipeIngredient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodClient {

    private static final String BASE_URL = "http://recipesapi.herokuapp.com/api/";
    private FoodApi foodApi;
    private static FoodClient instance;

    private FoodClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        this.foodApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
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
    public Observable<Ingredient> getIngredient(String rId){
        return foodApi.getRecipeIngredient(rId);
    }

    public void insertFood(Context context, Recipe recipe){
        FoodDB.getInstance(context).foodDao().insertFood(recipe);
    }

    public void deleteFood(Context context, String _id){
        FoodDB.getInstance(context).foodDao().deleteFood(_id);
    }

    public List<Recipe> getAllFav(Context context){
        return FoodDB.getInstance(context).foodDao().getAllFavRecipes();
    }
}
