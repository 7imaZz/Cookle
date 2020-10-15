package com.example.cookle.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cookle.pojo.Id;
import com.example.cookle.pojo.Recipe;
import com.example.cookle.pojo.Video;
import com.example.cookle.repository.FoodClient;
import com.example.cookle.pojo.Food;
import com.example.cookle.repository.VideoClient;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FoodViewModel extends ViewModel {

    private static final String TAG = "FoodViewModel";
    public MutableLiveData<ArrayList<Recipe>> foodLiveData = new MutableLiveData<>();

    public MutableLiveData<String> videoLiveData = new MutableLiveData<>();

    public void getFood(String food){
        FoodClient.getInstance().getFood(food)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Food>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Food food) {
                        foodLiveData.setValue(food.getRecipes());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "7i: onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getVideo(String food){
        VideoClient.getInstance().getVideo(food)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Video>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Video video) {
                        if (video.getItems().size()>0){
                        videoLiveData.setValue(video.getItems().get(0).getId().getVideoId());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "7i: onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
