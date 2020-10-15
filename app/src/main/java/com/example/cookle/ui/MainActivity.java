package com.example.cookle.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import com.example.cookle.R;
import com.example.cookle.viewmodel.FoodViewModel;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public FoodViewModel foodViewModel;

    public String[] randomFood = {"meat", "grill", "chicken", "cake", "pizza", "mushroom", "apple"
            , "rice", "beans", "banana", "fish", "burger"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        int rand = new Random().nextInt(randomFood.length);
        foodViewModel.getFood(randomFood[rand]);

    }

}
