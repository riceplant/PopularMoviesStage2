package com.riceplant.popularmovies.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.popularmovies.R;
import com.riceplant.popularmovies.adapter.ReviewsAdapter;
import com.riceplant.popularmovies.adapter.TrailerAdapter;
import com.riceplant.popularmovies.data.FavouritesContract;
import com.riceplant.popularmovies.data.FavouritesDbHelper;
import com.riceplant.popularmovies.model.Movie;
import com.riceplant.popularmovies.model.Reviews;
import com.riceplant.popularmovies.model.Trailer;
import com.riceplant.popularmovies.utils.MovieDetailsUtils;
import com.riceplant.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class FavouritesDetailsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewReviews;
    private Button mFavourites;
    private String movieId;
    private TrailerAdapter mTrailerAdapter;
    private Trailer[] trailer;
    private ReviewsAdapter mReviewsAdapter;
    private Reviews[] reviews;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private SQLiteDatabase mDb;
    String[] mProjection =
            {
                    FavouritesContract.FavouritesAdd._ID,
                    FavouritesContract.FavouritesAdd.COLUMN_MOVIE_ID
            };

    private String[] mSelectionArgs = {""};
    private String mSelectionClause;
    Uri mNewUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_detail);

        TextView movieTitleTv = findViewById(R.id.movie_title_details);
        TextView ratingTv = findViewById(R.id.rating_details);
        TextView synopsisTv = findViewById(R.id.synopsis_details);
        TextView releaseDateTv = findViewById(R.id.release_date_details);
        ImageView moviePosterIv = findViewById(R.id.poster_iv_details);
        mFavourites = findViewById(R.id.add_to_favourites);

        FavouritesDbHelper dbHelper = new FavouritesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        // trailers
        mRecyclerView = findViewById(R.id.recycler_view_trailer);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mTrailerAdapter);

        // reviews
        mRecyclerViewReviews = (RecyclerView) findViewById(R.id.recycler_view_reviews);

        LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewReviews.setLayoutManager(reviewsLayoutManager);
        mRecyclerViewReviews.setHasFixedSize(true);
        mRecyclerViewReviews.setAdapter(mReviewsAdapter);

        Intent intentToCatch = getIntent();
        final Movie movie = intentToCatch.getParcelableExtra(MainActivity.MY_MOVIE);

        final String movieTitle = movie.getMovieTitle();
        final String poster = movie.getPoster();
        final String rating = movie.getRating();
        final String synopsis = movie.getSynopsis();
        final String releaseDate = movie.getReleaseDate();
        movieId = movie.getId();

        movieTitleTv.setText(movieTitle);
        ratingTv.setText(rating);
        synopsisTv.setText(synopsis);
        releaseDateTv.setText(releaseDate);

        Picasso.get()
                .load(poster)
                .into(moviePosterIv);

        mFavourites.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isMovieFavourited(movieId)) {
                    removeFavorites(movieId);

                    Context context = getApplicationContext();
                    CharSequence removedFavorites = "This movie is removed from your favorites.";
                    Toast toast = Toast.makeText(context, removedFavorites, Toast.LENGTH_SHORT);
                    toast.show();

                    mFavourites.setText(getString(R.string.add_to_favourites));
                } else {
                    addToFavourites(movieTitle, movieId, poster, rating, releaseDate, synopsis);
                    Context context = getApplicationContext();
                    CharSequence addedFavorites = "This movie is added to your favorites.";
                    Toast toast = Toast.makeText(context, addedFavorites, Toast.LENGTH_SHORT);
                    toast.show();

                    mFavourites.setText(getString(R.string.remove_from_favourites));
                }
            }
        });

        loadTrailerData();
        loadReviewData();
        isMovieFavourited(movieId);
    }

    private void loadTrailerData() {
        String trailerData = movieId;
        new fetchTrailerDataTask().execute(trailerData);
    }

    private void loadReviewData() {
        String reviewData = movieId;
        new fetchReviewDataTask().execute(reviewData);
    }

    public class fetchTrailerDataTask extends AsyncTask<String, Void, Trailer[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Trailer[] doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            URL trailerRequestUrl = NetworkUtils.buildTrailerUrl(movieId);

            try {
                String jsonTrailerResponse = NetworkUtils.getResponseFromHttpUrl(trailerRequestUrl);
                trailer = MovieDetailsUtils.getSimpleTrailerDetailsFromJson(FavouritesDetailsActivity.this, jsonTrailerResponse);

                return trailer;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(Trailer[] trailers) {
            if (trailers != null) {
                mTrailerAdapter = new TrailerAdapter(trailers, FavouritesDetailsActivity.this);
                mRecyclerView.setAdapter(mTrailerAdapter);
            }
        }

    }

    public class fetchReviewDataTask extends AsyncTask<String, Void, Reviews[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Reviews[] doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            URL reviewRequestUrl = NetworkUtils.buildReviewsUrl(movieId);

            try {
                String jsonReviewResponse = NetworkUtils.getResponseFromHttpUrl(reviewRequestUrl);
                reviews = MovieDetailsUtils.getSimpleReviewDetailFromJson(FavouritesDetailsActivity.this, jsonReviewResponse);
                return reviews;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(Reviews[] review) {
            if (review != null) {
                mReviewsAdapter = new ReviewsAdapter(review, FavouritesDetailsActivity.this);
                mRecyclerViewReviews.setAdapter(mReviewsAdapter);
            }
        }
    }

    @Override
    protected void onPause() {
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerViewReviews.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerViewReviews.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    private void addToFavourites(String name, String id, String poster, String rate, String release, String overview){
        ContentValues cv = new ContentValues();
        cv.put(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_ID, id);
        cv.put(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_NAME, name);
        cv.put(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_POSTER, poster);
        cv.put(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_RATE, rate);
        cv.put(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_RELEASE, release);
        cv.put(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_OVERVIEW, overview);
        mNewUri = getContentResolver().insert(
                FavouritesContract.FavouritesAdd.CONTENT_URI,
                cv
        );
    }

    private void removeFavorites(String id){
        mSelectionClause = FavouritesContract.FavouritesAdd.COLUMN_MOVIE_ID + " LIKE ?";
        String[] selectionArgs = new String[] {id};
        getContentResolver().delete(
                FavouritesContract.FavouritesAdd.CONTENT_URI,
                mSelectionClause,
                selectionArgs
        );
    }

    public boolean isMovieFavourited(String id){
        mSelectionClause = FavouritesContract.FavouritesAdd.COLUMN_MOVIE_ID + " = ?";
        mSelectionArgs[0] = id;
        Cursor mCursor = getContentResolver().query(
                FavouritesContract.FavouritesAdd.CONTENT_URI,
                mProjection,
                mSelectionClause,
                mSelectionArgs,
                null);

        if(mCursor.getCount() <= 0){
            mCursor.close();
            mFavourites.setText(getString(R.string.add_to_favourites));
            return false;
        }
        mCursor.close();
        mFavourites.setText(getString(R.string.remove_from_favourites));
        return true;
    }
}
