package com.riceplant.popularmovies;

public class Movie {

    private String mMovieTitle;
    private String mPoster;
    private String mRating;
    private String mSynopsis;
    private String mReleaseDate;

    public Movie(String movieTitle, String poster, String rating, String synopsis, String releaseDate) {
        mMovieTitle = movieTitle;
        mPoster = poster;
        mRating = rating;
        mSynopsis = synopsis;
        mReleaseDate = releaseDate;
    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public String getPoster() {
        return mPoster;
    }

    public String getRating() {
        return mRating;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }
}
