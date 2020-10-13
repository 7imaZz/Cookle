package com.example.cookle.pojo;

public class Recipe {

    private String _id;
    private String title;
    private String image_url;
    private String social_rank;

    public Recipe(String _id, String title, String image_url, String social_rank) {
        this._id = _id;
        this.title = title;
        this.image_url = image_url;
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

    public String getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(String social_rank) {
        this.social_rank = social_rank;
    }
}
