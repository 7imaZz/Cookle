package com.example.cookle.pojo;

import java.util.ArrayList;

public class Food {

    private int count;
    private ArrayList<Recipe> recipes;

    public Food(int count, ArrayList<Recipe> recipes) {
        this.count = count;
        this.recipes = recipes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
