package com.riceplant.popularmovies;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;

    MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovies();
    }

    LiveData<List<Movie>> getAllMovies() {
        return mAllMovies;
    }

    void insert(Movie movie) {
        MovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMovieDao.insert(movie);
        });
    }
}
