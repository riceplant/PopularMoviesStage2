package com.riceplant.popularmovies.utils;

import android.content.Context;

import com.riceplant.popularmovies.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrailerDetailsUtils {

    public static Trailer[] getSimpleTrailerDetailsFromJson(Context context, String trailerJsonString) throws JSONException {

        final String TMDB_RESULTS = "results";
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
}
