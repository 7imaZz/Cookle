package com.example.cookle.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.cookle.R;
import com.example.cookle.viewmodel.FoodViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;

    public FoodViewModel foodViewModel;

    public String[] randomFood = {"meat", "grill", "chicken", "cake", "pizza", "mushroom", "apple"
            , "rice", "beans", "banana", "fish", "burger"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);


        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        int rand = new Random().nextInt(randomFood.length);
        foodViewModel.getFood(randomFood[rand]);

    }

}
