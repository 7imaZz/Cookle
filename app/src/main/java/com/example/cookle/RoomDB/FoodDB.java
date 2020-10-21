package com.example.cookle.RoomDB;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cookle.pojo.Recipe;

@Database(entities = Recipe.class, version = 1, exportSchema = false)
public abstract class FoodDB extends RoomDatabase {

    public abstract FoodDao foodDao();

    private static FoodDB instance;

    public static synchronized FoodDB getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), FoodDB.class, "food_table")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
