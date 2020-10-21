package com.example.cookle.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_table")
public class Recipe {

    @PrimaryKey()
    @NonNull
    private String _id;

    private String title;
    private String image_url;
    private String publisher;
    private String recipe_id;
    private Double social_rank;


    public Recipe(String _id, String title, String image_url, String publisher, String recipe_id, Double social_rank) {
        this._id = _id;
        this.title = title;
        this.image_url = image_url;
        this.publisher = publisher;
        this.social_rank = social_rank;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Double getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(Double social_rank) {
        this.social_rank = social_rank;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }
}
