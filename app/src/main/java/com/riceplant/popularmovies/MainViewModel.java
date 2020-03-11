package com.riceplant.popularmovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.riceplant.popularmovies.MovieRoomDatabase;
import com.riceplant.popularmovies.model.Movie;

public class MainViewModel extends AndroidViewModel {

    private LiveData<Movie[]> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieRoomDatabase database = MovieRoomDatabase.getInstance(this.getApplication());
        movies = database.movieDao().loadAllMovies();
    }

    public LiveData<Movie[]> getMovies() {
        return movies;
    }
}
