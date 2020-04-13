package com.example.shopmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomGridAdapter extends BaseAdapter {
    private final List<Movie> movies;
    private final LayoutInflater layoutInflater;

    public CustomGridAdapter(Context ctx, List<Movie> movies) {
        this.movies = movies;
        layoutInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.grid_item_layout, null);
        Movie movie = movies.get(position);
        new DownloadImageTask(view.findViewById(R.id.imageMovie)).execute(movie.getImage());
        ((TextView) view.findViewById(R.id.titleMovie)).setText(movie.getTitle());
        ((TextView) view.findViewById(R.id.priceMovie)).setText(movie.getPrice());
        return view;
    }


}