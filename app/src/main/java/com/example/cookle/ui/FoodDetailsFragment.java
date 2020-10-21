package com.example.cookle.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookle.R;
import com.example.cookle.network.InternetConnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDetailsFragment extends Fragment {

    private ImageView foodImageView;
    private TextView foodTitleTextView, foodSocialRankTextView, foodPublisherTextView, ingredientsTextView;

    public FoodDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_details, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(!InternetConnection.checkConnection(requireContext())){
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        //Receive Arguments
        Bundle bundle = requireArguments();
        String recipeId = bundle.getString("recipe_id");
        String imageUrl = bundle.getString("image_url");
        String foodTitle = bundle.getString("food_title");
        String socialRank = bundle.getString("social_rank");
        String publisher = bundle.getString("publisher");


        ((MainActivity)requireActivity()).foodViewModel.getIngredients(recipeId);

        //Initialize Views
        foodImageView = requireView().findViewById(R.id.food_details_img);
        foodTitleTextView = requireView().findViewById(R.id.food_title_details_tv);
        foodPublisherTextView = requireView().findViewById(R.id.publish_details_tv);
        foodSocialRankTextView = requireView().findViewById(R.id.social_rank_details_tv);
        ingredientsTextView = requireView().findViewById(R.id.ingredients_tv);

        ((MainActivity)requireActivity()).bottomNavigationView.setVisibility(View.GONE);

        setupViews(imageUrl, foodTitle, socialRank, publisher);
        onBackButtonClicked(view);

        ((MainActivity)requireActivity()).foodViewModel.ingredientsLiveData.observe(requireActivity(), ingredients -> {
            StringBuilder t = new StringBuilder();
            for (int i=0; i<ingredients.size(); i++){
                t.append("- ").append(ingredients.get(i)).append("\n");
            }
            ingredientsTextView.setText(t.toString());
        });

    }

    private void onBackButtonClicked(View view){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                ((MainActivity)requireActivity()).foodViewModel.ingredientsLiveData.postValue(new ArrayList<>());
                Navigation.findNavController(requireView()).navigate(R.id.action_foodDetailsFragment_to_mainFoodFragment);
                return true;
            }
            return false;
        });
    }

    private void setupViews(String imageUrl, String title, String socialRank, String publisher){

        Glide.with(requireContext())
                .load(imageUrl)
                .placeholder(R.drawable.food_placeholer)
                .into(foodImageView);

        foodTitleTextView.setText(title);
        foodSocialRankTextView.setText(socialRank);
        foodPublisherTextView.setText(publisher);
    }


}
