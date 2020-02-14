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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView movieTitleTv = findViewById(R.id.movie_title_details);
        TextView ratingTv = findViewById(R.id.rating_details);
        TextView synopsisTv = findViewById(R.id.synopsis_details);
        TextView releaseDateTv = findViewById(R.id.release_date_details);
        ImageView moviePosterIv = findViewById(R.id.poster_iv_details);

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
