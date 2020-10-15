package com.example.cookle.pojo;

import java.util.ArrayList;

public class Video {
    private ArrayList<Item> items;

    public Video(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
