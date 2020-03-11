package com.riceplant.popularmovies;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.riceplant.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {
    private static final String LOG_TAG = MovieRoomDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movies";
    private static MovieRoomDatabase sInstance;

    public static MovieRoomDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieRoomDatabase.class, MovieRoomDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return  sInstance;
    }

    public abstract MovieDao movieDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };

    static final Migration MIGRATION_2_3 = new Migration (2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };
    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL(
                    "CREATE UNIQUE INDEX index_movie_movieId ON movie (movieId) ");
        }
    };
    static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL (
                    "CREATE TABLE movie_new (movieId INTEGER NOT NULL, originalTitle TEXT, posterPath TEXT, " +
                            "overview TEXT, releaseDate TEXT, " +
                            "voterAverage REAL, trailerPath TEXT, " +
                            "reviewAuthor TEXT, " +
                            "reviewContents TEXT, " +
                            "reviewUrl TEXT, dbMovieId  INTEGER NOT NULL,  PRIMARY KEY (dbMovieId ) )"
            );

            database.execSQL (
                    "INSERT INTO movie_new (movieId, originalTitle, posterPath, overview," +
                            "releaseDate, voterAverage, trailerPath, reviewAuthor, reviewContents," +
                            "reviewUrl) SELECT * FROM movie"
            );

            database.execSQL (
                    "DROP TABLE movie"
            );
            database.execSQL (
                    "ALTER TABLE movie_new RENAME TO movie"
            );
        }
    };
    static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL (
                    "CREATE TABLE movie_new (movieId INTEGER NOT NULL, originalTitle TEXT, posterPath TEXT, " +
                            "overview TEXT, releaseDate TEXT, " +
                            "voterAverage REAL, trailerPath TEXT, " +
                            "reviewAuthor TEXT, " +
                            "reviewContents TEXT, " +
                            "reviewUrl TEXT, dbMovieId  INTEGER NOT NULL,  PRIMARY KEY (dbMovieId ) )"
            );
            database.execSQL (
                    "INSERT INTO movie_new (movieId, originalTitle, posterPath, overview," +
                            "releaseDate, voterAverage, trailerPath, reviewAuthor, reviewContents," +
                            "reviewUrl) SELECT * FROM movie"

            );



            database.execSQL (
                    "DROP TABLE movie"
            );
            database.execSQL (
                    "ALTER TABLE movie_new RENAME TO movie"
            );
        }
    };
}
