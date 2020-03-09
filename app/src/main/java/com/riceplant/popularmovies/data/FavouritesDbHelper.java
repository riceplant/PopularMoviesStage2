package com.riceplant.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.riceplant.popularmovies.data.FavouritesContract.FavouritesAdd;

public class FavouritesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favourites.db";
    private static final int DATABASE_VERSION = 3;

    public FavouritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " +
                FavouritesAdd.TABLE_NAME + " (" +
                FavouritesAdd._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavouritesAdd.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                FavouritesAdd.COLUMN_MOVIE_NAME + " TEXT NOT NULL," +
                FavouritesAdd.COLUMN_MOVIE_POSTER + " TEXT NOT NULL," +
                FavouritesAdd.COLUMN_MOVIE_RATE + " TEXT NOT NULL," +
                FavouritesAdd.COLUMN_MOVIE_RELEASE + " TEXT NOT NULL," +
                FavouritesAdd.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouritesAdd.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
