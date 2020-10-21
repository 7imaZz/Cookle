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
import java.util.HashSet;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        public ImageView foodImageView, favImageView;
        TextView foodTitleTextView, publisherTextView;

        FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.food_img);
            foodTitleTextView = itemView.findViewById(R.id.food_title_tv);
            publisherTextView = itemView.findViewById(R.id.publish_tv);
            favImageView = itemView.findViewById(R.id.fav_img);
        }
    }


    private ArrayList<Recipe> recipes = new ArrayList<>();
    private HashSet<String> favs = new HashSet<>();
    private Context context;
    private RecyclerViewOnItemClick recyclerViewOnItemClick;
    private FoodRecyclerOnFavImageClick foodRecyclerOnFavImageClick;


    public FoodAdapter(Context context, RecyclerViewOnItemClick recyclerViewOnItemClick) {
        this.context = context;
        this.recyclerViewOnItemClick = recyclerViewOnItemClick;
    }

    public FoodAdapter(Context context, HashSet<String> favs, RecyclerViewOnItemClick recyclerViewOnItemClick, FoodRecyclerOnFavImageClick foodRecyclerOnFavImageClick) {
        this.context = context;
        this.favs = favs;
        this.recyclerViewOnItemClick = recyclerViewOnItemClick;
        this.foodRecyclerOnFavImageClick = foodRecyclerOnFavImageClick;
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

        Glide.with(context)
                .load(recipes.get(position).getImage_url())
                .fitCenter()
                .placeholder(R.drawable.food_placeholer)
                .into(holder.foodImageView);

        if (foodRecyclerOnFavImageClick == null){
            holder.favImageView.setVisibility(View.GONE);
        }

        for (int i=0; i<recipes.size(); i++){
            if (favs.contains(recipes.get(position).get_id())){
                holder.favImageView.setImageResource(R.drawable.ic_favorite_red);
            }else {
                holder.favImageView.setImageResource(R.drawable.ic_favorite_grey);
            }
        }
        holder.favImageView.setOnClickListener(v -> foodRecyclerOnFavImageClick.onFavImageClick(holder, position));

        holder.itemView.setOnClickListener(v -> recyclerViewOnItemClick.onItemClick(position));

        holder.itemView.setOnLongClickListener(v -> {
            recyclerViewOnItemClick.onLongItemClick(position);
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void clearRecipes(){
        recipes.clear();
    }

}
