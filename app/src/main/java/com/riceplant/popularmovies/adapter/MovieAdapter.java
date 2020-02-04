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

    private Movie[] movieData;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMoviePoster;

        public MovieAdapterViewHolder(ImageView mMoviePoster) {
            super(mMoviePoster);
            this.mMoviePoster = mMoviePoster;
            mMoviePoster = mMoviePoster.findViewById(R.id.poster_iv);
            mMoviePoster.setOnClickListener(this);
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
        return new MovieAdapterViewHolder((ImageView) view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewholder, int position) {
        String currentMovie = movieData[position].getPoster();
        Picasso.get()
                .load(currentMovie)
                .into(movieAdapterViewholder.mMoviePoster);
    }

    @Override
    public int getItemCount() {
        if (movieData == null) {
            return 0;
        }
        return movieData.length;
    }
}
