package com.riceplant.popularmovies.utils;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie?api_key=";
    private static final String API_KEY = "4c43b6641940650366e77e920910f07f";

    private static final String SORT_PARAM = "&sort_by=";
    private static final String sortedByPopularity = "popularity.desc";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(API_KEY)
                .appendQueryParameter(SORT_PARAM, sortedByPopularity)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
