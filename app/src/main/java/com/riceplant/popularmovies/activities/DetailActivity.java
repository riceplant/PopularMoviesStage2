package com.riceplant.popularmovies.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.popularmovies.Movie;
import com.riceplant.popularmovies.R;
import com.riceplant.popularmovies.Trailer;
import com.riceplant.popularmovies.adapter.TrailerAdapter;
import com.riceplant.popularmovies.utils.NetworkUtils;
import com.riceplant.popularmovies.utils.TrailerDetailsUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView mRecylerView;
    private String movieId;
    private TrailerAdapter mTrailerAdapter;
    private Trailer[] trailer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView movieTitleTv = findViewById(R.id.movie_title_details);
        TextView ratingTv = findViewById(R.id.rating_details);
        TextView synopsisTv = findViewById(R.id.synopsis_details);
        TextView releaseDateTv = findViewById(R.id.release_date_details);
        ImageView moviePosterIv = findViewById(R.id.poster_iv_details);

        mRecylerView = findViewById(R.id.recycler_view_trailer);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(layoutManager);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setAdapter(mTrailerAdapter);

        Intent intentToCatch = getIntent();
        Movie movie = intentToCatch.getParcelableExtra(MainActivity.MY_MOVIE);

        String movieTitle = movie.getMovieTitle();
        String poster = movie.getPoster();
        String rating = movie.getRating();
        String synopsis = movie.getSynopsis();
        String releaseDate = movie.getReleaseDate();
        movieId = movie.getId();

        movieTitleTv.setText(movieTitle);
        ratingTv.setText(rating);
        synopsisTv.setText(synopsis);
        releaseDateTv.setText(releaseDate);

        Picasso.get()
                .load(poster)
                .into(moviePosterIv);
        loadTrailerData();
    }

    private void loadTrailerData() {
        String trailerData = movieId;
        new fetchTrailerDataTask().execute(trailerData);
        Log.v("TrailerId", movieId);
    }

    public class fetchTrailerDataTask extends AsyncTask<String, Void, Trailer[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Trailer[] doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            URL trailerRequestUrl = NetworkUtils.buildTrailerUrl(movieId);

            try {
                String jsonTrailerResponse = NetworkUtils.getResponseFromHttpUrl(trailerRequestUrl);
                trailer = TrailerDetailsUtils.getSimpleTrailerDetailsFromJson(DetailActivity.this, jsonTrailerResponse);

                return trailer;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(Trailer[] trailers) {
            if (trailers != null) {
                mTrailerAdapter = new TrailerAdapter(trailers, DetailActivity.this);
                mRecylerView.setAdapter(mTrailerAdapter);
            } else {

            }
        }

    }


}
