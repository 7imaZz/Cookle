package com.example.cookle.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.cookle.R;
import com.example.cookle.adapters.FoodAdapter;
import com.example.cookle.pojo.Recipe;
import com.example.cookle.viewmodel.FoodViewModel;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    SearchView searchView;
    FoodViewModel foodViewModel;
    RecyclerView foodRecyclerView;
    FoodAdapter adapter;
    ProgressBar progressBar;
    LinearLayout noFoodLinearLayout;
    SwipeRefreshLayout refresher;

    int rand;
    String[] randomFood = {"meat", "grill", "chicken", "cake", "pizza", "mushroom", "apple"
            , "rice", "beans", "banana", "fish", "burger"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        rand = new Random().nextInt(randomFood.length);
        foodViewModel.getFood(randomFood[rand]);

        searchView = findViewById(R.id.search_sv);
        expandSearchView();

        progressBar = findViewById(R.id.pb);
        noFoodLinearLayout = findViewById(R.id.no_foo_ll);

        refresher = findViewById(R.id.refresher);

        foodRecyclerView = findViewById(R.id.food_rv);
        adapter = new FoodAdapter(this);
        foodRecyclerView.setAdapter(adapter);

        observeFood();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                noFoodLinearLayout.setVisibility(View.GONE);
                foodViewModel.getFood(query);
                observeFood();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        refresher.setOnRefreshListener(() -> {
            foodViewModel.getFood(randomFood[new Random().nextInt(randomFood.length)]);
            observeFood();
            refresher.setRefreshing(false);
        });

    }

    public void observeFood(){
        adapter.clearRecipes();
        foodViewModel.liveData.observe(this, recipes -> {
            if (recipes.size() > 0){
                adapter.setRecipes(recipes);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                noFoodLinearLayout.setVisibility(View.GONE);
            }else{
                noFoodLinearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void expandSearchView(){
        searchView.setOnClickListener(v -> searchView.setIconified(false));
    }
}
