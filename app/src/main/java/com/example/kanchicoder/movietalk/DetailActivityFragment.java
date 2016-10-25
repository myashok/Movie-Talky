package com.example.kanchicoder.movietalk;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by kanchicoder on 10/22/2016.
 */

public class DetailActivityFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();

        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        if (intent != null && intent.hasExtra("value")) {
            MovieDetails movieDetails = (MovieDetails) intent.getSerializableExtra("value");

            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/"+movieDetails.posterLink)
                    .into((ImageView) rootView.findViewById(R.id.detail_activity_imageview));
            ((TextView) rootView.findViewById(R.id.detail_movie_title)).setText(movieDetails.originalName);
            ((TextView) rootView.findViewById(R.id.detail_activity_overview)).setText(movieDetails.movieOverview);
            ((TextView) rootView.findViewById(R.id.detail_activity_release_date)).setText(movieDetails.releaseDate);
            ((TextView) rootView.findViewById(R.id.detail_activity_rating)).setText(Double.toString(movieDetails.userRating));
        }
        return rootView;
    }
}
