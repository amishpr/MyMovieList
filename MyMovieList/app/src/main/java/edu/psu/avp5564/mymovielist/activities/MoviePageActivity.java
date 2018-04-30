package edu.psu.avp5564.mymovielist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.psu.avp5564.mymovielist.R;
import edu.psu.avp5564.mymovielist.model.Movie;

public class MoviePageActivity extends AppCompatActivity {

    ImageView moviePoster;

    TextView movieTitle;
    TextView movieReleaseDate;
    TextView movieOverview;
    TextView movieRating;

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        // Using getSerializableExtra(String key) method
        Movie movie = (Movie) getIntent().getSerializableExtra("serialize_data");

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
    }
}
