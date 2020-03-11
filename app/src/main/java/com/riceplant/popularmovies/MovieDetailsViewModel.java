package com.riceplant.popularmovies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.riceplant.popularmovies.model.Movie;

public class MovieDetailsViewModel extends ViewModel {

    private LiveData<Movie> movie;

    public MovieDetailsViewModel(MovieRoomDatabase database, String movieId) {
        movie = database.movieDao ().loadMovieById (movieId);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
