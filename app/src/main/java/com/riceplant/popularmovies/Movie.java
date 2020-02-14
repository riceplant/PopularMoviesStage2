package com.riceplant.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String mMovieTitle;
    private String mPoster;
    private String mRating;
    private String mSynopsis;
    private String mReleaseDate;

    public Movie() {
    }

    private Movie(Parcel in) {
        mMovieTitle = in.readString();
        mPoster = in.readString();
        mRating = in.readString();
        mSynopsis = in.readString();
        mReleaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMovieTitle);
        parcel.writeString(mPoster);
        parcel.writeString(mRating);
        parcel.writeString(mSynopsis);
        parcel.writeString(mReleaseDate);
    }
}
