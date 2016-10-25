package com.example.kanchicoder.movietalk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kanchicoder on 10/22/2016.
 */

public class MainActivityFragment extends Fragment {
    MovieDetailsAdapter movieDetailsAdapter;
    private RequestQueue requestQueue;
    private String jsonURL;
    private MovieDetails[] results;

    public MainActivityFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void updateMovie(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = pref.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popularity_value));
        fetchMovieTask(sortBy);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        PreferenceManager.setDefaultValues(getActivity(), R.xml.pref_general, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean autoStart = sharedPreferences.getBoolean("pref_boot_startup", true);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ArrayList<MovieDetails> movieDetails = new ArrayList<MovieDetails>();
        movieDetailsAdapter = new MovieDetailsAdapter(getActivity(), movieDetails);
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        gridView.setAdapter(movieDetailsAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieDetails movieDetails = (MovieDetails) movieDetailsAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", movieDetails);
                Intent intent = new Intent(getActivity(), DetailActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
        return rootView;
    }
    public  void fetchMovieTask(String sortBy) {
        requestQueue = Volley.newRequestQueue(getContext());
        String baseUrl = "http://api.themoviedb.org/3/movie/" + sortBy + "?";
        String apiKey = "api_key=" + "232d071acf9e94e74fdf0c0f6ed71e8c";
        jsonURL = baseUrl + apiKey;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonURL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject movieJson) {
                        final String MOV_RESULTS = "results";
                        final String MOV_TITLE = "original_title";
                        final String MOV_DATE = "release_date";
                        final String MOV_OVERVIEW = "overview";
                        final String MOV_POSTER = "poster_path";
                        final String MOV_VOTE = "vote_average";

                        JSONArray movieArray = null;
                        try {
                            movieArray = movieJson.getJSONArray(MOV_RESULTS);
                            results = new MovieDetails[movieArray.length()];
                            for (int i = 0; i < movieArray.length(); i++) {
                                JSONObject jsonmovieDetails = movieArray.getJSONObject(i);
                                MovieDetails movieDetails = new MovieDetails();
                                movieDetails.originalName = jsonmovieDetails.getString(MOV_TITLE);
                                movieDetails.releaseDate = jsonmovieDetails.getString(MOV_DATE);
                                movieDetails.movieOverview = jsonmovieDetails.getString(MOV_OVERVIEW);
                                movieDetails.posterLink = jsonmovieDetails.getString(MOV_POSTER);
                                movieDetails.userRating = jsonmovieDetails.getDouble(MOV_VOTE);
                                results[i] = movieDetails;

                            }
                            movieDetailsAdapter.clear();
                            if (results != null) {
                                movieDetailsAdapter.addAll(results);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Volley", "Error in Json Parsing");
                    }
                });
        requestQueue.add(jsonObjectRequest);

    }

}
