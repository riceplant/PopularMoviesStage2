package com.riceplant.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavouritesContract {
    public static final String AUTHORITY = "com.riceplant.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVOURITES = "favourites";

    public static final class FavouritesAdd implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVOURITES)
                .build();

        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_NAME = "movieName";
        public static final String COLUMN_MOVIE_POSTER = "moviePoster";
        public static final String COLUMN_MOVIE_RATE = "movieRate";
        public static final String COLUMN_MOVIE_RELEASE = "movieRelease";
        public static final String COLUMN_MOVIE_OVERVIEW = "movieOverview";
    }
}
