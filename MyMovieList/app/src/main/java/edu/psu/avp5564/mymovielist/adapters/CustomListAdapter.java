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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.psu.avp5564.mymovielist.R;
import edu.psu.avp5564.mymovielist.model.Movie;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private List<Movie> movieArray = new ArrayList<>();

    public CustomListAdapter(Activity context, String[] itemname, List<Movie> movieArray) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.movieArray=movieArray;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(movieArray.get(position).getTitle());
        Picasso.get().load(movieArray.get(position).getPosterURL()).into(imageView);
//        imageView.setImageResource(imgid[position]);
        extratxt.setText(movieArray.get(position).getReleaseDate());
        return rowView;

    };
}