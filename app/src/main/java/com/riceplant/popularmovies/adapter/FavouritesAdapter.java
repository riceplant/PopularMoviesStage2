package com.riceplant.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.popularmovies.R;
import com.riceplant.popularmovies.activities.FavouritesDetailsActivity;
import com.riceplant.popularmovies.data.FavouritesContract;
import com.squareup.picasso.Picasso;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouriteViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    public String name = "";
    public String poster = "";
    public int id;

    public FavouritesAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(FavouritesContract.FavouritesAdd._ID);
        int posterIndex = mCursor.getColumnIndex(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_POSTER);

        mCursor.moveToPosition(position);

        id = mCursor.getInt(idIndex);
        poster = mCursor.getString(posterIndex);

        holder.itemView.setTag(id);
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_not_found)
                .into(holder.mMovieListImageView);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        if (mCursor == c) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;

        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mMovieListImageView;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);

            mMovieListImageView = (ImageView) itemView.findViewById(R.id.poster_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            Class favouriteDetailClass = FavouritesDetailsActivity.class;

            String name = mCursor.getString(mCursor.getColumnIndex(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_NAME));
            String movieId = mCursor.getString(mCursor.getColumnIndex(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_ID));
            String overview = mCursor.getString(mCursor.getColumnIndex(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_OVERVIEW));
            String rate = mCursor.getString(mCursor.getColumnIndex(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_RATE));
            String release = mCursor.getString(mCursor.getColumnIndex(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_RELEASE));
            String poster = mCursor.getString(mCursor.getColumnIndex(FavouritesContract.FavouritesAdd.COLUMN_MOVIE_POSTER));

            Intent intentToStartDetailActivity = new Intent(mContext, favouriteDetailClass);
            intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, adapterPosition);
            intentToStartDetailActivity.putExtra("title", name);
            intentToStartDetailActivity.putExtra("poster", poster);
            intentToStartDetailActivity.putExtra("rate", rate);
            intentToStartDetailActivity.putExtra("release", release);
            intentToStartDetailActivity.putExtra("overview", overview);
            intentToStartDetailActivity.putExtra("id", movieId);

            mContext.startActivity(intentToStartDetailActivity);
        }
    }
}
