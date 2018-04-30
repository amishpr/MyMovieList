package edu.psu.avp5564.mymovielist.adapters;

/**
 * Created by swegmaster on 4/30/18.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.psu.avp5564.mymovielist.R;
import edu.psu.avp5564.mymovielist.model.Movie;

public class MovieListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private List<Movie> movieArray = new ArrayList<>();

    public MovieListAdapter(Activity context, String[] itemname, List<Movie> movieArray) {
        super(context, R.layout.item_search_movie_list, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.movieArray = movieArray;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_search_movie_list, null,true);

        TextView movieTitleList = (TextView) rowView.findViewById(R.id.titleTextList);
        ImageView moviePosterList = (ImageView) rowView.findViewById(R.id.posterTextList);
        TextView movieReleaseDateList = (TextView) rowView.findViewById(R.id.releaseDateTextList);
        TextView movieOverviewList = (TextView) rowView.findViewById(R.id.overviewTextList);

        movieTitleList.setText(movieArray.get(position).getTitle());
        Picasso.get().load(movieArray.get(position).getPosterURL()).into(moviePosterList);
//        imageView.setImageResource(imgid[position]);
        movieReleaseDateList.setText(movieArray.get(position).getReleaseDate());
        movieOverviewList.setText(movieArray.get(position).getOverview());
        return rowView;

    };
}