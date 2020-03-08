package com.riceplant.popularmovies.utils;

import android.content.Context;

import com.riceplant.popularmovies.Movie;
import com.riceplant.popularmovies.Reviews;
import com.riceplant.popularmovies.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsUtils {

    public static final String TMDB_RESULTS = "results";

    public static Movie[] getSimpleMovieDetailsFromJson(String movieJsonString) throws JSONException {

        final String BASE_URL = "https://image.tmdb.org/t/p/";
        final String POSTER_SIZE = "w500";

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

    public static Trailer[] getSimpleTrailerDetailsFromJson(Context context, String trailerJsonString) throws JSONException {

        final String TMDB_ID = "id";
        final String TMDB_KEY = "key";
        final String TMDB_NAME = "name";

        JSONObject trailerJson = new JSONObject(trailerJsonString);
        JSONArray resultsArray = trailerJson.optJSONArray(TMDB_RESULTS);

        Trailer[] trailers = new Trailer[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {

            Trailer trailer = new Trailer();

            JSONObject jsonObject = resultsArray.optJSONObject(i);

            String id = jsonObject.optString(TMDB_ID);
            String key = jsonObject.optString(TMDB_KEY);
            String name = jsonObject.optString(TMDB_NAME);

            trailer.setId(id);
            trailer.setKey(key);
            trailer.setName(name);

            trailers[i] = trailer;
        }
        return trailers;
    }

    public static Reviews[] getSimpleReviewDetailFromJson(Context context, String reviewsJsonString) throws JSONException {

        final String TMDB_AUTHOR = "author";
        final String TMDB_CONTENT = "content";

        JSONObject reviewJson = new JSONObject(reviewsJsonString);
        JSONArray resultsArray = reviewJson.optJSONArray(TMDB_RESULTS);

        Reviews[] reviews = new Reviews[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {

            Reviews review = new Reviews();

            JSONObject jsonObject = resultsArray.optJSONObject(i);

            String author = jsonObject.optString(TMDB_AUTHOR);
            String content = jsonObject.optString(TMDB_CONTENT);

            review.setAuthor(author);
            review.setContent(content);

            reviews[i] = review;
        }
        return reviews;
    }
}
