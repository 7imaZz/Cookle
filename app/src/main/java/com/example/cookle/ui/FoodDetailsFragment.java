package com.example.cookle.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookle.R;
import com.example.cookle.viewmodel.FoodViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDetailsFragment extends Fragment {

    private YouTubePlayerView youTubePlayerView;

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

        Bundle bundle = requireArguments();
        String recipeId = bundle.getString("recipe_id");
        String foodTitle = bundle.getString("food_title");

        youTubePlayerView = requireActivity().findViewById(R.id.youtube_player);
        requireActivity().getLifecycle().addObserver(youTubePlayerView);

        onBackButtonClicked(view);


        ((MainActivity) requireActivity()).foodViewModel.videoLiveData.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        if (s!=null)
                            youTubePlayer.loadVideo(s, 0);
                    }
                });
            }
        });




    }

    private void onBackButtonClicked(View view){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                youTubePlayerView.release();
                Navigation.findNavController(requireView()).navigate(R.id.action_foodDetailsFragment_to_mainFoodFragment);
                return true;
            }
            return false;
        });
    }
}
