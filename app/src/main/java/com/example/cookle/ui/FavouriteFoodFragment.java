package com.example.cookle.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookle.R;
import com.example.cookle.adapters.FoodAdapter;
import com.example.cookle.adapters.RecyclerViewOnItemClick;
import com.example.cookle.pojo.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFoodFragment extends Fragment implements RecyclerViewOnItemClick {

    private RecyclerView favouriteRecyclerView;

    private ArrayList<Recipe> recipes;
    private FoodAdapter adapter;

    public FavouriteFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favouriteRecyclerView = requireActivity().findViewById(R.id.fav_rv);

        recipes = new ArrayList<>();

        adapter = new FoodAdapter(requireContext(), this);
        recipes = (ArrayList<Recipe>) ((MainActivity)requireActivity()).foodViewModel.getAllFav(requireContext());
        Collections.reverse(recipes);
        adapter.setRecipes(recipes);
        favouriteRecyclerView.setAdapter(adapter);

        setupOnSwipe();

        ((MainActivity)requireActivity()).bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.fav_menu:
                    break;
                case R.id.home_menu:
                    Navigation.findNavController(requireView()).navigate(R.id.action_favouriteFoodFragment_to_mainFoodFragment);
                    break;
            }
            return true;
        });
    }


    private void setupOnSwipe(){

        ItemTouchHelper.SimpleCallback callback
                = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ACTION_STATE_DRAG,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Delete This Food?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    Recipe currentRecipe = recipes.get(viewHolder.getAdapterPosition());
                    ((MainActivity)requireActivity()).foodViewModel.deleteFood(requireContext(), currentRecipe.get_id());
                    recipes.remove(currentRecipe);
                    adapter.notifyDataSetChanged();
                });

                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                });
                builder.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(favouriteRecyclerView);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }

}
