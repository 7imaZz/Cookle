package com.example.cookle.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cookle.R;
import com.example.cookle.adapters.FoodAdapter;
import com.example.cookle.adapters.FoodRecyclerOnFavImageClick;
import com.example.cookle.adapters.RecyclerViewOnItemClick;
import com.example.cookle.network.InternetConnection;
import com.example.cookle.pojo.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFoodFragment extends Fragment implements RecyclerViewOnItemClick, FoodRecyclerOnFavImageClick {

    private SearchView searchView;
    private FoodAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout noFoodLinearLayout;
    private LinearLayout noInternetLinearLayout;
    private SwipeRefreshLayout refresher;
    private ArrayList<Recipe> recipes;
    private RecyclerView foodRecyclerView;

    private Set<String> favs;

    public MainFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        int pos = sharedPref.getInt("rPos", 0);
        favs = new HashSet<>();

        ArrayList<Recipe> favRecipes = (ArrayList<Recipe>)
                ((MainActivity)requireActivity()).foodViewModel.getAllFav(requireContext());
        for (int i=0; i<favRecipes.size(); i++){
            favs.add(favRecipes.get(i).get_id());
        }

        searchView = requireActivity().findViewById(R.id.search_sv);
        progressBar = requireActivity().findViewById(R.id.pb);
        noFoodLinearLayout = requireActivity().findViewById(R.id.no_foo_ll);
        refresher = requireActivity().findViewById(R.id.refresher);
        foodRecyclerView = requireActivity().findViewById(R.id.food_rv);
        noInternetLinearLayout = requireActivity().findViewById(R.id.no_internet_ll);

        ((MainActivity)requireActivity()).bottomNavigationView.setVisibility(View.VISIBLE);


        recipes = new ArrayList<>();
        adapter = new FoodAdapter(getContext(), (HashSet<String>)favs,this, this);


        foodRecyclerView.setAdapter(adapter);
        foodRecyclerView.scrollToPosition(pos);


        if(!InternetConnection.checkConnection(requireContext())){
            showNoConnectionView();
        }

        expandSearchView();
        observeFood();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(!InternetConnection.checkConnection(requireContext())){
                    showNoConnectionView();
                    return false;
                }

                progressBar.setVisibility(View.VISIBLE);
                noFoodLinearLayout.setVisibility(View.GONE);
                ((MainActivity) requireActivity()).foodViewModel.getFood(query);
                observeFood();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        refresher.setOnRefreshListener(() -> {
            if(!InternetConnection.checkConnection(requireContext())){
                showNoConnectionView();
                return;
            }
            if (!searchView.getQuery().toString().isEmpty()){
                ((MainActivity) requireActivity()).foodViewModel.getFood(searchView.getQuery().toString());
            }else {
                ((MainActivity) requireActivity()).foodViewModel.getFood(((MainActivity) requireActivity())
                        .randomFood[new Random().nextInt(((MainActivity) requireActivity()).randomFood.length)]);
            }
            observeFood();
            refresher.setRefreshing(false);
        });

        ((MainActivity)requireActivity()).bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.fav_menu:
                    Navigation.findNavController(requireView()).navigate(R.id.action_mainFoodFragment_to_favouriteFoodFragment);

                    int pos1 = ((LinearLayoutManager) Objects.requireNonNull(foodRecyclerView.getLayoutManager())).findFirstVisibleItemPosition();
                    SharedPreferences sharedPref1 = requireActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref1.edit();
                    editor.putInt("rPos", pos1);
                    editor.apply();

                    break;
                case R.id.home_menu:
                    break;
            }
            return true;
        });


    }

    private void observeFood(){
        adapter.clearRecipes();
        ((MainActivity) requireActivity()).foodViewModel.foodLiveData.observe(requireActivity(), recipes -> {
            this.recipes = recipes;
            if (recipes.size() > 0){
                adapter.setRecipes(recipes);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                noFoodLinearLayout.setVisibility(View.GONE);
                noInternetLinearLayout.setVisibility(View.GONE);
            }else{
                noFoodLinearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void expandSearchView(){
        searchView.setOnClickListener(v -> searchView.setIconified(false));
    }

    @Override
    public void onItemClick(int position) {

        Bundle bundle = new Bundle();
        bundle.putString("recipe_id", recipes.get(position).getRecipe_id());
        bundle.putString("food_title", recipes.get(position).getTitle());
        bundle.putString("image_url", recipes.get(position).getImage_url());
        bundle.putString("social_rank", recipes.get(position).getSocial_rank().intValue()+"");
        bundle.putString("publisher", recipes.get(position).getPublisher());

        int pos = ((LinearLayoutManager) Objects.requireNonNull(foodRecyclerView.getLayoutManager())).findFirstVisibleItemPosition();

        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("rPos", pos);
        editor.apply();

        Navigation.findNavController(requireView()).navigate(R.id.action_mainFoodFragment_to_foodDetailsFragment, bundle);
    }

    @Override
    public void onLongItemClick(int position) {
    }

    private void showNoConnectionView(){
        noInternetLinearLayout.setVisibility(View.VISIBLE);
        noFoodLinearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        adapter.clearRecipes();
        refresher.setRefreshing(false);
    }

    @Override
    public void onFavImageClick(FoodAdapter.FoodViewHolder holder, int position) {

        if (!favs.contains(recipes.get(position).get_id())){
            favs.add(recipes.get(position).get_id());
            holder.favImageView.setImageResource(R.drawable.ic_favorite_red);
            Toast.makeText(requireContext(), "Added Successfully to Favourite", Toast.LENGTH_SHORT).show();
            ((MainActivity) requireActivity()).foodViewModel.insertFood(requireContext(), recipes.get(position));
        }else {
            favs.remove(recipes.get(position).get_id());
            holder.favImageView.setImageResource(R.drawable.ic_favorite_grey);
            Toast.makeText(requireContext(), "Removed From Favorite", Toast.LENGTH_SHORT).show();
            ((MainActivity) requireActivity()).foodViewModel.deleteFood(requireContext(), recipes.get(position).get_id());
        }

    }
}
