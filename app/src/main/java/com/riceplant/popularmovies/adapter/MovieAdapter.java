package com.riceplant.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.popularmovies.Movie;
import com.riceplant.popularmovies.R;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private final Movie[] mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;

    public MovieAdapter(Movie[] movies, MovieAdapterOnClickHandler clickHandler) {
        mMovieData = movies;
        mClickHandler = clickHandler;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mMoviePoster;

        MovieAdapterViewHolder(View view) {
            super(view);
            mMoviePoster = view.findViewById(R.id.poster_iv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForIdListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForIdListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewholder, int position) {
        String currentMovie = mMovieData[position].getPoster();
        Picasso.get()
                .load(currentMovie)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_not_found)
                .into(movieAdapterViewholder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.length;
    }
}
