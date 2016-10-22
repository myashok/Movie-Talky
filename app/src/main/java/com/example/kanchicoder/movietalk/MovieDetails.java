package com.example.kanchicoder.movietalk;

import java.io.Serializable;

/**
 * Created by kanchicoder on 10/22/2016.
 */

public class MovieDetails implements Serializable{
    String originalName, releaseDate, movieOverview, posterLink;
    double userRating;
    public MovieDetails() {
    }
}
