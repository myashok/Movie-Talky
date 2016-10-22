package com.example.kanchicoder.movietalk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by kanchicoder on 10/22/2016.
 */

public class MovieImageAdapter extends ArrayAdapter<MovieImage> {
    private static final String LOG_TAG = MovieImageAdapter.class.getSimpleName();
    public MovieImageAdapter(Context context, List<MovieImage> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieImage movieImage = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main_movie_item, parent, false);
        }
        ImageView image = (ImageView)convertView.findViewById(R.id.movie_image);
        image.setImageResource(movieImage.image);
        return convertView;
    }
}
