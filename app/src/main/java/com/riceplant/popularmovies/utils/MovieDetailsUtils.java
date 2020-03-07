package com.riceplant.popularmovies.utils;

import com.riceplant.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsUtils {

    public static Movie[] getSimpleMovieDetailsFromJson(String movieJsonString) throws JSONException {

        final String BASE_URL = "https://image.tmdb.org/t/p/";
        final String POSTER_SIZE = "w500";

        final String TMDB_RESULTS = "results";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_TITLE = "title";
        final String TMDB_VOTE_AVERAGE = "vote_average";
        final String TMDB_SYNOPSIS = "overview";
        final String TMDB_RELEASE = "release_date";
        final String TMDB_MOVIE_ID = "id";

        JSONObject movieJson = new JSONObject(movieJsonString);
        JSONArray resultsArray = movieJson.optJSONArray(TMDB_RESULTS);

        Movie[] movies = new Movie[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {

            Movie movie = new Movie();

            JSONObject jsonObject = resultsArray.optJSONObject(i);

            String posterPath = jsonObject.optString(TMDB_POSTER_PATH);
            String title = jsonObject.optString(TMDB_TITLE);
            String voteAverage = jsonObject.optString(TMDB_VOTE_AVERAGE);
            String synopsis = jsonObject.optString(TMDB_SYNOPSIS);
            String releaseDate = jsonObject.optString(TMDB_RELEASE);
            String movieId = jsonObject.optString(TMDB_MOVIE_ID);

            movie.setMovieTitle(title);
            movie.setPoster(BASE_URL + POSTER_SIZE + posterPath);
            movie.setRating(voteAverage);
            movie.setSynopsis(synopsis);
            movie.setReleaseDate(releaseDate);
            movie.setId(movieId);

            movies[i] = movie;
        }
        return movies;
    }
}
