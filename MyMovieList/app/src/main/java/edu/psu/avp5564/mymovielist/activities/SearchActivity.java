package edu.psu.avp5564.mymovielist.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.psu.avp5564.mymovielist.R;
import edu.psu.avp5564.mymovielist.adapters.MovieListAdapter;
import edu.psu.avp5564.mymovielist.model.Movie;

public class SearchActivity extends AppCompatActivity {

    Button searchButton;
    EditText searchText;
    ProgressDialog pd;

    MovieListAdapter adapter;
    ListView searchMovieListView;

    public static List<Movie> allMovies = new ArrayList<Movie>();
    String[] numberOfMovies;

    final String apiURLPart1 = "https://api.themoviedb.org/3/search/movie?api_key=625045ffe79af1aa6c205bfd8da97070&language=en-US&query=";
    final String apiURLPart2 = "&page=1&include_adult=false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchText   = (EditText) findViewById(R.id.searchText);
        searchMovieListView = (ListView) findViewById(R.id.searchMovieListView);

        Movie movie = new Movie();
        movie.setId("0");
        movie.setTitle(" ");
        movie.setReleaseDate(" ");
        movie.setPosterURL(" ");

        numberOfMovies = new String[]{"0"};

        allMovies.add(movie);

        adapter = new MovieListAdapter(this, numberOfMovies, allMovies);

        searchMovieListView.setAdapter(adapter);


        searchButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String searchTextQuery = searchText.getText().toString();

                        String searchURL = apiURLPart1 + searchTextQuery + apiURLPart2;

                        new JsonTask().execute(searchURL.toString());
                    }
                });

        searchMovieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Movie selectedMovie = allMovies.get(+position);
                String selectedMovieTitle = allMovies.get(+position).getTitle();

                Toast.makeText(getApplicationContext(), selectedMovieTitle, Toast.LENGTH_SHORT).show();

                createMoviePage(selectedMovie);

            }
        });
    }

    public void createMoviePage(Movie movie) {

        // Reference:
        // https://www.techjini.com/blog/passing-objects-via-intent-in-android/

        Intent intent = new Intent(this, MoviePageActivity.class);
        intent.putExtra("serialize_data", movie);
        startActivity(intent);
    }

    public void updateAdapter() {

        numberOfMovies = new String[allMovies.size()];

        for(int i = 0; i < allMovies.size(); i++)
        {
            numberOfMovies[i] = "yes";
        }

        adapter.notifyDataSetChanged();
        adapter = new MovieListAdapter(this, numberOfMovies, allMovies);
        searchMovieListView.setAdapter(adapter);
    }

    // Reference:
    // https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(SearchActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");

                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            allMovies = new ArrayList<Movie>();

            if(result != null)
            {
                try {

                    JSONObject reader = new JSONObject(result);

                    JSONArray results = reader.getJSONArray("results");
                    for ( int i = 0; i < results.length(); i++) {
                        JSONObject resultObject = results.getJSONObject(i);
                        Movie movie = new Movie();
                        String id = resultObject.getString("id");
                        String title = resultObject.getString("title");
                        String poster_path = resultObject.getString("poster_path");
                        String overview = resultObject.getString("overview");
                        String release_date = resultObject.getString("release_date");
                        String vote_average = resultObject.getString("vote_average");
                        movie.setId(id);
                        movie.setTitle(title);
                        movie.setPosterURL(poster_path);
                        movie.setOverview(overview);
                        movie.setReleaseDate(release_date);
                        movie.setRating(vote_average);

                        boolean isNotEmpty = true;

                        if(movie.getId() == "" || movie.getId() == null) {
                            isNotEmpty = false;
                        }
                        else if(movie.getTitle() == "" || movie.getTitle() == null) {
                            isNotEmpty = false;
                        }
                        else if(movie.getPosterURL() == "" || movie.getPosterURL() == null) {
                            isNotEmpty = false;
                        }
                        else if(movie.getOverview() == "" || movie.getOverview() == null) {
                            isNotEmpty = false;
                        }
                        else if(movie.getReleaseDate() == "" || movie.getReleaseDate() == null) {
                            isNotEmpty = false;
                        }
                        if(isNotEmpty)
                        {
                            allMovies.add(movie);
                        }
                    }
                    Log.d("Array: ", allMovies.toString());

                    updateAdapter();

                } catch (Throwable tx) {
                    Log.e("JSON_ERROR", "Could not parse malformed JSON: \"" + result + "\"");
                }
            }else {
                Toast.makeText(getApplicationContext(), "No movies found", Toast.LENGTH_LONG).show();
            }
        }
    }
}

//}
