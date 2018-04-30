package edu.psu.avp5564.mymovielist.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import edu.psu.avp5564.mymovielist.adapters.CustomListAdapter;
import edu.psu.avp5564.mymovielist.model.Movie;

public class Search extends AppCompatActivity {

    Button searchButton;
    EditText searchText;
    TextView txtJson;
    ProgressDialog pd;

    CustomListAdapter adapter;
//    ArrayAdapter<Movie> adapter;
    ListView listView1;

    public static List<Movie> allMovies = new ArrayList<Movie>();
    String[] itemname;

    final String apiURLPart1 = "https://api.themoviedb.org/3/search/movie?api_key=625045ffe79af1aa6c205bfd8da97070&language=en-US&query=";
    final String apiURLPart2 = "&page=1&include_adult=false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchText   = (EditText) findViewById(R.id.searchText);
//        txtJson = (TextView) findViewById(R.id.tvJsonItem);

        listView1 = (ListView) findViewById(R.id.listView1);

        Movie movie = new Movie();
        movie.setId("0");
        movie.setTitle("Test");
        movie.setReleaseDate("1997-03-12");
        movie.setPosterURL("https://image.tmdb.org/t/p/w200_and_h300_bestv2/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg");

        itemname = new String[]{
                "TEST",
        };

        allMovies.add(movie);

        adapter = new CustomListAdapter(this, itemname, allMovies);

//        adapter = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1, allMovies);

        listView1.setAdapter(adapter);


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
    }

    public void updateAdapter() {
//        adapter = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1, allMovies);

        itemname = new String[allMovies.size()];

        for(int i = 0; i < allMovies.size(); i++)
        {
            itemname[i] = "yes";
        }

        adapter.notifyDataSetChanged();
        adapter = new CustomListAdapter(this, itemname, allMovies);
        listView1.setAdapter(adapter);
    }

    // Citation:
    // https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(Search.this);
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

//                    Log.d("My App", obj.toString());
//                    Log.d("title value ", obj.getString("title"));


                    JSONArray results = reader.getJSONArray("results");
                    for ( int i = 0; i < results.length(); i++) {
                        JSONObject resultObject = results.getJSONObject(i);
                        Movie movie = new Movie();
                        String id = resultObject.getString("id");
                        String title = resultObject.getString("title");
                        String poster_path = resultObject.getString("poster_path");
                        String overview = resultObject.getString("overview");
                        String release_date = resultObject.getString("release_date");
                        movie.setId(id);
                        movie.setTitle(title);
                        movie.setPosterURL(poster_path);
                        movie.setOverview(overview);
                        movie.setReleaseDate(release_date);
                        allMovies.add(movie);
                    }
                    Log.d("Array: ", allMovies.toString());

                    updateAdapter();
//                    new updateAdapterTask().execute();
//                    txtJson.setText(allMovies.toString());

                } catch (Throwable tx) {
                    Log.e("JSON_ERROR", "Could not parse malformed JSON: \"" + result + "\"");
                }
            }else {

//                txtJson.setText(result);
            }
        }
    }
}

//}
