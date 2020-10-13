package com.example.cookle.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cookle.R;
import com.example.cookle.pojo.Recipe;
import com.example.cookle.viewmodel.FoodViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView testTextView;
    FoodViewModel foodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testTextView = findViewById(R.id.tst);

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getFood();

        foodViewModel.liveData.observe(this, new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(ArrayList<Recipe> recipes) {
                testTextView.setText(recipes.get(0).getTitle());
            }
        });
    }
}
