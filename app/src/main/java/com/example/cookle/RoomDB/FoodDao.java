package com.example.cookle.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cookle.pojo.Recipe;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insertFood(Recipe recipe);

    @Query("DELETE FROM food_table WHERE _id=:id")
    void deleteFood(String id);

    @Query("SELECT * FROM food_table")
    List<Recipe> getAllFavRecipes();
}
