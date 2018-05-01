package edu.psu.avp5564.mymovielist.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

import edu.psu.avp5564.mymovielist.R;
import edu.psu.avp5564.mymovielist.model.Movie;

public class MoviePageActivity extends AppCompatActivity {

    ImageView moviePoster;

    TextView movieTitle;
    TextView movieReleaseDate;
    TextView movieOverview;
    TextView movieRating;

    ConstraintLayout moviePageLayout;

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        // Using getSerializableExtra(String key) method
        final Movie movie = (Movie) getIntent().getSerializableExtra("serialize_data");

        moviePageLayout = (ConstraintLayout) findViewById(R.id.moviePageLayout);
        readTheme(moviePageLayout);

        moviePoster = (ImageView) findViewById(R.id.moviePoster);
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        movieReleaseDate = (TextView) findViewById(R.id.movieReleaseDate);
        movieOverview = (TextView) findViewById(R.id.movieOverview);
        movieRating = (TextView) findViewById(R.id.movieRating);
        addButton = (Button) findViewById(R.id.addButton);

        Picasso.get().load(movie.getPosterURL()).into(moviePoster);
        movieTitle.setText(movie.getTitle());
        movieReleaseDate.setText(movie.getReleaseDate());
        movieOverview.setText(movie.getOverview());
        movieRating.setText("Rating: " + movie.getRating());

        if(movie.getIsAdded() == true)
        {
            addButton.setText("Remove");
        }

        addButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        if(movie.getIsAdded() == true)
                        {
                            Toast.makeText(getApplicationContext(), movie.getTitle() + " was removed.", Toast.LENGTH_SHORT).show();
                            removeMovie(movie);
                            addButton.setText("Add");
                        } else {
                            Toast.makeText(getApplicationContext(), movie.getTitle() + " was added.", Toast.LENGTH_SHORT).show();
                            saveMovie(movie);
                            addButton.setText("Remove");
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        readTheme(moviePageLayout);
        super.onResume();
    }

    // Reference:
    // https://stackoverflow.com/questions/14981233/android-arraylist-of-custom-objects-save-to-sharedpreferences-serializable
    private void saveMovie(Movie movie) {

        movie.setAdded(true);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movie);
        prefsEditor.putString(movie.getId(), json);
        prefsEditor.commit();
    }

    private void removeMovie(Movie movie) {

        movie.setAdded(false);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        prefsEditor.remove(movie.getId());

        prefsEditor.commit();

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
