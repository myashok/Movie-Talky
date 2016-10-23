package com.example.kanchicoder.movietalk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kanchicoder on 10/22/2016.
 */

public class MainActivityFragment extends Fragment {
    MovieDetailsAdapter movieDetailsAdapter;
    private String sortingCriteria;
    public MainActivityFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void updateMovie(){
        FetchMovieTask movieTask = new FetchMovieTask();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = pref.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popularity_value));
        movieTask.execute(sortBy);
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
    public class FetchMovieTask extends AsyncTask<String, Void, MovieDetails[]> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        private MovieDetails[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            final String MOV_RESULTS = "results";
            final String MOV_TITLE = "original_title";
            final String MOV_DATE = "release_date";
            final String MOV_OVERVIEW = "overview";
            final String MOV_POSTER = "poster_path";
            final String MOV_VOTE = "vote_average";
            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(MOV_RESULTS);

            MovieDetails[] results = new MovieDetails[movieArray.length()];

            for(int i = 0; i < movieArray.length(); i++) {
                JSONObject jsonmovieDetails = movieArray.getJSONObject(i);

                MovieDetails movieDetails = new MovieDetails();
                movieDetails.originalName = jsonmovieDetails.getString(MOV_TITLE);
                movieDetails.releaseDate = jsonmovieDetails.getString(MOV_DATE);
                movieDetails.movieOverview = jsonmovieDetails.getString(MOV_OVERVIEW);
                movieDetails.posterLink = jsonmovieDetails.getString(MOV_POSTER);
                movieDetails.userRating = jsonmovieDetails.getDouble(MOV_VOTE);

                results[i] = movieDetails;


            }
            return results;

        }

        @Override
        protected MovieDetails[] doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            try {
                //Embed your api Key
                String baseUrl = "http://api.themoviedb.org/3/movie/"+params[0]+"?";
                String apiKey = "api_key=" + "YourApiKey";
                URL url = new URL(baseUrl.concat(apiKey));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                movieJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                if(movieJsonStr != null)
                    return  getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(MovieDetails[] result) {
           movieDetailsAdapter.clear();
            if(result != null) {
                for (int i = 0; i < result.length; i++) {
                    movieDetailsAdapter.add(result[i]);
                }
            }

        }
    }
}
