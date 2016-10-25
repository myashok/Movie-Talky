package com.example.kanchicoder.movietalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kanchicoder on 10/22/2016.
 */

public class MovieDetailsAdapter extends ArrayAdapter<MovieDetails> {
    private static final String LOG_TAG = MovieDetailsAdapter.class.getSimpleName();
    public MovieDetailsAdapter(Context context, List<MovieDetails> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieDetails movieImage = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main_movie_item, parent, false);
        }
        ImageView image = (ImageView)convertView.findViewById(R.id.movie_image);
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w185/"+movieImage.posterLink).into(image);
        return convertView;
    }
}
