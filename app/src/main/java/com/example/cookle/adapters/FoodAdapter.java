package com.example.cookle.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookle.R;
import com.example.cookle.pojo.Recipe;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImageView;
        TextView foodTitleTextView, publisherTextView, socialRankTextView;

        FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.food_img);
            foodTitleTextView = itemView.findViewById(R.id.food_title_tv);
            publisherTextView = itemView.findViewById(R.id.publish_tv);
            socialRankTextView = itemView.findViewById(R.id.social_rank_tv);
        }
    }

    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Context context;

    public FoodAdapter(Context context) {
        this.context = context;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {

        holder.foodTitleTextView.setText(recipes.get(position).getTitle());
        holder.publisherTextView.setText(recipes.get(position).getPublisher());
        holder.socialRankTextView.setText(recipes.get(position).getSocial_rank().intValue()+"");

        Glide.with(context)
                .load(recipes.get(position).getImage_url())
                .fitCenter()
                .placeholder(R.drawable.food_placeholer)
                .into(holder.foodImageView);
    }

    public void clearRecipes(){
        recipes.clear();
    }
    @Override
    public int getItemCount() {
        return recipes.size();
    }

}
