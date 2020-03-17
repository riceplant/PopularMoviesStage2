package com.riceplant.popularmovies;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.popularmovies.adapter.MovieAdapter;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;

    private MovieViewModel mMovieViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);


    }
}
