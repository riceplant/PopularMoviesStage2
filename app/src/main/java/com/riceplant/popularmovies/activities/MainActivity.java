package com.riceplant.popularmovies.activities;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.popularmovies.model.Movie;
import com.riceplant.popularmovies.R;
import com.riceplant.popularmovies.adapter.MovieAdapter;
import com.riceplant.popularmovies.utils.MovieDetailsUtils;
import com.riceplant.popularmovies.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicator;
    private MovieAdapter mMovieAdapter;
    private Movie[] movies;

    private static Bundle mBundleRecyclerViewState;

    public static final String MY_MOVIE = "myMovie";
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";
    private final String KEY_RECYCLER_STATE = "recycler_state";

    private String movieSearchQuery = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            movieSearchQuery = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mErrorMessageTextView = findViewById(R.id.tv_error_message_display);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = findViewById(R.id.progress_bar);

        loadMovieData();
    }

    private void loadMovieData() {
        String sortByPopular = movieSearchQuery;
        showMovieData();
        new fetchMovieDataTask().execute(sortByPopular);

    }

    @Override
    public void onClick(int adapterPosition) {
        Context context = this;
        Class detailClass = DetailActivity.class;

        Intent detailIntent = new Intent(context, detailClass);
        detailIntent.putExtra(MY_MOVIE, movies[adapterPosition]);
        startActivity(detailIntent);

    }

    private void showMovieData() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    public class fetchMovieDataTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }


        @Override
        protected Movie[] doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            String sortBy = strings[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sortBy);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                movies = MovieDetailsUtils.getSimpleMovieDetailsFromJson(jsonMovieResponse);

                return movies;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(Movie[] movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                mMovieAdapter = new MovieAdapter(movies, MainActivity.this);
                mRecyclerView.setAdapter(mMovieAdapter);
            } else {
                showErrorMessage();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemSelected = item.getItemId();

        switch (menuItemSelected) {
            case R.id.action_sorting_popular:
                movieSearchQuery = "popular";
                loadMovieData();
                return true;
            case R.id.action_sorting_top_rated:
                movieSearchQuery = "top_rated";
                loadMovieData();
                return true;
            case R.id.action_sorting_favourites:
                Context context = this;
                Class destinationClass = FavouritesActivity.class;
                Intent intentToStartFavouritesActivity = new Intent(context, destinationClass);
                startActivity(intentToStartFavouritesActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        movieSearchQuery = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String lifecycleSortBy = movieSearchQuery;
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY, lifecycleSortBy);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}
