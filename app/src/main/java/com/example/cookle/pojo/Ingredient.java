package com.example.cookle.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("recipe")
    @Expose
    private RecipeIngredient recipe;

    public Ingredient(RecipeIngredient recipe) {
        this.recipe = recipe;
    }

    public RecipeIngredient getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeIngredient recipe) {
        this.recipe = recipe;
    }
}
