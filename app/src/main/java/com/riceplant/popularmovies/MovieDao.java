package com.riceplant.popularmovies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert()
    void insert(Movie movie);

    @Query("DELETE FROM movie")
    void deleteAllMovies();

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getAllMovies();
}
