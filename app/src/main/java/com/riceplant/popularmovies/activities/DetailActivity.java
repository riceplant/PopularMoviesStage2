package com.riceplant.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.riceplant.popularmovies.Movie;
import com.riceplant.popularmovies.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView movieTitleTv;
    private TextView ratingTv;
    private TextView synopsisTv;
    private TextView releaseDateTv;
    private ImageView moviePosterIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieTitleTv = findViewById(R.id.movie_title_details);
        ratingTv = findViewById(R.id.rating_details);
        synopsisTv = findViewById(R.id.synopsis_details);
        releaseDateTv = findViewById(R.id.release_date_details);
        moviePosterIv = findViewById(R.id.poster_iv_details);

        Intent intentToCatch = getIntent();
        Movie movie = intentToCatch.getParcelableExtra(MainActivity.MY_MOVIE);

        String movieTitle = movie.getMovieTitle();
        String poster = movie.getPoster();
        String rating = movie.getRating();
        String synopsis = movie.getSynopsis();
        String releaseDate = movie.getReleaseDate();

        movieTitleTv.setText(movieTitle);
        ratingTv.setText(rating);
        synopsisTv.setText(synopsis);
        releaseDateTv.setText(releaseDate);

        Picasso.get()
                .load(poster)
                .into(moviePosterIv);
    }
}
