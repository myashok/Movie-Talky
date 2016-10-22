package com.example.kanchicoder.movietalk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.Arrays;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by kanchicoder on 10/22/2016.
 */

public class MainActivityFragment extends Fragment {
    MovieImageAdapter movieImageAdapter;
    public MainActivityFragment() {
    }
    private MovieImage []images = {
            new MovieImage(R.drawable.civil_war),
            new MovieImage(R.drawable.inferno),
            new MovieImage(R.drawable.interstellar),
            new MovieImage(R.drawable.suicide_squad),
            new MovieImage(R.drawable.magnificient_seven),
            new MovieImage(R.drawable.star_trek_beyond)
    };


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        movieImageAdapter = new MovieImageAdapter(getActivity(), Arrays.asList(images));
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        gridView.setAdapter(movieImageAdapter);
        return rootView;
    }
}
