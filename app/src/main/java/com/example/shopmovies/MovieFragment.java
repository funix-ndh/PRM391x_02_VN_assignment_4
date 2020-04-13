package com.example.shopmovies;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {

    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new getMovie().execute();
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    private class getMovie extends AsyncTask<Void, Void, Void> {
        final List<Movie> movies = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String data = HttpHandler.makeServiceCall("https://api.androidhive.info/json/movies_2017.json");
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    movies.add(new Movie(jsonObject.getString("title"), jsonObject.getString("image"), jsonObject.getString("price")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            GridView gridView = getActivity().findViewById(R.id.gridView);
            gridView.setAdapter(new CustomGridAdapter(getContext(), movies));
        }
    }
}
