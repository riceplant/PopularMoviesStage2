package com.riceplant.popularmovies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY mId")
    LiveData<List<FavouriteMovie>> loadAllMovies();

    @Insert
    void insertMovie(FavouriteMovie favMovie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(FavouriteMovie favMovie);

    @Delete
    void deleteMovie(FavouriteMovie favMovie);

    @Query("SELECT * FROM movie WHERE mId = :id")
    FavouriteMovie loadMovieById(int id);
}
