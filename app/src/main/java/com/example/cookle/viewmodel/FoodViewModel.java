package com.example.cookle.viewmodel;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cookle.pojo.Ingredient;
import com.example.cookle.pojo.Recipe;
import com.example.cookle.repository.FoodClient;
import com.example.cookle.pojo.Food;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FoodViewModel extends ViewModel {

    private static final String TAG = "FoodViewModel";
    public MutableLiveData<ArrayList<Recipe>> foodLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<String>> ingredientsLiveData = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public void getFood(String food){
        Observable<Food> observable = FoodClient.getInstance().getFood(food)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.add(observable.subscribe(o -> foodLiveData.setValue(o.getRecipes()), e ->
                Log.d(TAG, "7i: onError: "+e)));
    }

    public void getIngredients(String rId){
        Observable<Ingredient> observable = FoodClient.getInstance().getIngredient(rId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(o-> ingredientsLiveData.setValue(o.getRecipe().getIngredients()), e ->
                Log.d(TAG, "7i: onError: "+e)));
    }

    public void insertFood(Context context, Recipe recipe){
        FoodClient.getInstance().insertFood(context, recipe);
    }

    public void deleteFood(Context context, String _id){
        FoodClient.getInstance().deleteFood(context, _id);
    }

    public List<Recipe> getAllFav(Context context){
        return FoodClient.getInstance().getAllFav(context);
    }
}
