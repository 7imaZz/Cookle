package com.example.cookle.pojo;

import java.util.ArrayList;

public class RecipeIngredient {

    private ArrayList<String> ingredients;

    public RecipeIngredient(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
