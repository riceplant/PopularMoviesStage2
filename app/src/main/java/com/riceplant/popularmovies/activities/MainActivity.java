package com.riceplant.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.popularmovies.FavouriteMovie;
import com.riceplant.popularmovies.MovieViewModel;
import com.riceplant.popularmovies.R;
import com.riceplant.popularmovies.adapter.MovieAdapter;
import com.riceplant.popularmovies.model.Movie;
import com.riceplant.popularmovies.utils.MovieDetailsUtils;
import com.riceplant.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageTextView;
    private ProgressBar mLoadingIndicator;
    private MovieAdapter mMovieAdapter;
    private List<Movie> movies;
    private List<FavouriteMovie> favMovies;

    public static final String MY_MOVIE = "myMovie";

    private String movieSearchQuery = "popular";
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            movieSearchQuery = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
        }
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mErrorMessageTextView = findViewById(R.id.tv_error_message_display);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = findViewById(R.id.progress_bar);

        favMovies = new ArrayList<>();

        loadMovieData();
        setupViewModel();
    }

    private void loadMovieData() {
        if (movieSearchQuery == "favourites") {
            clearMovieItemList();
            for (int i = 0; i < favMovies.size(); i++) {
                Movie movie  = new Movie(
                        favMovies.get(i).getMovieTitle(),
                        favMovies.get(i).getPoster(),
                        favMovies.get(i).getRating(),
                        favMovies.get(i).getSynopsis(),
                        favMovies.get(i).getReleaseDate(),
                        favMovies.get(i).getId()
                );
                movies.add(movie);
            }
             mMovieAdapter.setMovieData(movies);
        } else {
            String sortByPopular = movieSearchQuery;
            showMovieData();
            new fetchMovieDataTask().execute(sortByPopular);
        }

    }

    @Override
    public void onClick(int adapterPosition) {
        Context context = this;
        Class detailClass = DetailActivity.class;

        Intent detailIntent = new Intent(context, detailClass);
        detailIntent.putExtra(MY_MOVIE, movies.get(adapterPosition));
        startActivity(detailIntent);

    }

    private void clearMovieItemList() {
        if (movies != null) {
            movies.clear();
        } else {
            movies = new ArrayList<>();
        }
    }

    private void showMovieData() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    public class fetchMovieDataTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }


        @Override
        protected List<Movie> doInBackground(String... strings) {
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
        protected void onPostExecute(List<Movie> movies) {
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
            case R.id.action_sorting_favourite:
                movieSearchQuery = "favourites";
                loadMovieData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewModel() {
        MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteMovie> favs) {
                if(favs.size() > 0) {
                    favMovies.clear();
                    favMovies = favs;
                }
                for (int i = 0; i < favMovies.size(); i++) {
                    Log.d(TAG, favMovies.get(i).getMovieTitle());
                }
                loadMovieData();
            }
        });
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
