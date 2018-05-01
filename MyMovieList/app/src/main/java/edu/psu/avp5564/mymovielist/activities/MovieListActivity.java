package edu.psu.avp5564.mymovielist.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import edu.psu.avp5564.mymovielist.R;
import edu.psu.avp5564.mymovielist.adapters.MovieListAdapter;
import edu.psu.avp5564.mymovielist.model.Movie;

public class MovieListActivity extends AppCompatActivity {

    ProgressDialog pd;
    ConstraintLayout movieListLayout;

    MovieListAdapter adapter;
    //    ArrayAdapter<Movie> adapter;
    ListView movieListView;

    public static List<Movie> myMovies = new ArrayList<Movie>();
    String[] numberOfMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieListLayout = (ConstraintLayout) findViewById(R.id.movieListLayout);
        readTheme(movieListLayout);

        movieListView = (ListView) findViewById(R.id.movieListView);

        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Movie selectedMovie = myMovies.get(+position);
                String selectedMovieTitle = myMovies.get(+position).getTitle();

                Toast.makeText(getApplicationContext(), selectedMovieTitle, Toast.LENGTH_SHORT).show();

                createMoviePage(selectedMovie);
            }
        });

    }

    @Override
    protected void onResume() {
        updateAdapter();
        readTheme(movieListLayout);
        super.onResume();
    }

    public void createMoviePage(Movie movie) {

        // Reference:
        // https://www.techjini.com/blog/passing-objects-via-intent-in-android/

        Intent intent = new Intent(this, MoviePageActivity.class);
        intent.putExtra("serialize_data", movie);
        startActivity(intent);
    }

    public void updateAdapter() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());

            Movie movie = new Movie();

            List<Movie> currentList = new ArrayList<>();

            Map<String, ?> allEntries = appSharedPrefs.getAll();
            for (Map.Entry<String, ?> entry : allEntries.entrySet())
            {
                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());

               if(!(entry.getKey().toString().equals(getString(R.string.THEME_KEY))))
               {
                   Gson gson = new Gson();
                   String json = appSharedPrefs.getString(entry.getKey(), "");
                   movie = gson.fromJson(json, Movie.class);

                   currentList.add(movie);
               }
            }

            Set<Movie> movieListWithoutDuplicates = new LinkedHashSet<Movie>(currentList);

            // now let's clear the ArrayList so that we can copy all elements from LinkedHashSet
            myMovies.clear();

            // copying elements but without any duplicates
            myMovies.addAll(movieListWithoutDuplicates);

            numberOfMovies = new String[myMovies.size()];

            for(int j = 0; j < myMovies.size(); j++)
            {
                numberOfMovies[j] = "movies";
            }

            adapter = new MovieListAdapter(this, numberOfMovies, myMovies);
            movieListView.setAdapter(adapter);
    }

    public void readTheme(ConstraintLayout constraintLayout) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());

        String theme = appSharedPrefs.getString(getString(R.string.THEME_KEY), null);

        setTheme(theme, constraintLayout);
    }

    public void setTheme(String theme, ConstraintLayout constraintLayout) {

        if (theme.equals(getString(R.string.WHITE_CAP))) {
//            setTheme(R.style.AppTheme);
            constraintLayout.setBackgroundColor(Color.WHITE);
        }
        else if (theme.equals(getString(R.string.GRAY_CAP))) {
            constraintLayout.setBackgroundColor(Color.GRAY);
        }
        else if (theme.equals(getString(R.string.GREEN_CAP))) {
            constraintLayout.setBackgroundColor(Color.GREEN);
        }
        else if (theme.equals(getString(R.string.BLUE_CAP))) {
            constraintLayout.setBackgroundColor(Color.BLUE);
        }
        else if (theme.equals(getString(R.string.RED_CAP))) {
            constraintLayout.setBackgroundColor(Color.RED);
        }
    }
}
