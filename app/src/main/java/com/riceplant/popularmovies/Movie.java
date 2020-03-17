package com.riceplant.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int mDatabaseId;
    private String mMovieTitle;
    private String mPoster;
    private String mRating;
    private String mSynopsis;
    private String mReleaseDate;
    private String mId;

    public Movie() {
    }

    private Movie(Parcel in) {
        mDatabaseId = in.readInt();
        mMovieTitle = in.readString();
        mPoster = in.readString();
        mRating = in.readString();
        mSynopsis = in.readString();
        mReleaseDate = in.readString();
        mId = in.readString();
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

    public int getDatabaseId() {
        return mDatabaseId;
    }

    public void setDatabaseId(int databaseId) {
        mDatabaseId = databaseId;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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
        parcel.writeString(mId);
    }

}
