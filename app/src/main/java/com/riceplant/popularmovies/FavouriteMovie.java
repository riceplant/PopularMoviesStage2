package com.riceplant.popularmovies;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class FavouriteMovie {

    @PrimaryKey(autoGenerate = true)
    private int mId;
    private String mMovieTitle;
    private String mPoster;
    private String mRating;
    private String mSynopsis;
    private String mReleaseDate;

    public FavouriteMovie() {
    }

    public FavouriteMovie(String movieTitle, String poster, String rating, String synopsis, String releaseDate, int id) {
        mMovieTitle = movieTitle;
        mPoster = poster;
        mRating = rating;
        mSynopsis = synopsis;
        mReleaseDate = releaseDate;
        mId = id;
    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        mMovieTitle = movieTitle;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        mSynopsis = synopsis;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

}

