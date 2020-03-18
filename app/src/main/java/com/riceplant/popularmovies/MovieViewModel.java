package com.riceplant.popularmovies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MovieViewModel.class.getSimpleName();

    private LiveData<List<FavouriteMovie>> movies;

    public MovieViewModel(Application application) {
        super(application);
        MovieRoomDatabase database = MovieRoomDatabase.getInstance(this.getApplication());
//        Log.d(TAG, "Actively retrieving favorite movies from the DataBase");
        movies = database.movieDao().loadAllMovies();
    }

    public LiveData<List<FavouriteMovie>> getMovies() {
        return movies;
    }
}
