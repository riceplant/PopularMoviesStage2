package com.riceplant.popularmovies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.riceplant.popularmovies.model.Movie;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie_table")
    LiveData<Movie[]> loadAllMovies();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Query("DELETE FROM movie_table WHERE mId = :movieId")
    void delete(String movieId);

    @Query("SELECT * FROM movie_table WHERE mId = :movieId")
    LiveData<Movie> loadMovieById(String movieId);
}
