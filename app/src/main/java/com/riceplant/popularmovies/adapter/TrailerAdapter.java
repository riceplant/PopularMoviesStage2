package com.riceplant.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.popularmovies.R;
import com.riceplant.popularmovies.Trailer;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    final String TRAILER_BASE_URL = "https://www.youtube.com/watch?v=";
    private Trailer[] mTrailerData;
    public static TextView mTrailerListTextView = null;
    Context mContext;

    public TrailerAdapter(Trailer[] trailerData, Context context) {
        mTrailerData = trailerData;
        mContext = context;
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder {

        public TrailerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrailerListTextView = (TextView) itemView.findViewById(R.id.tv_trailer_name);
            mTrailerListTextView.setPaintFlags(mTrailerListTextView.getPaintFlags() |
                    Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForIdListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForIdListItem, parent, shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
        String trailerToBind = mTrailerData[position].getName();
        final String trailerToWatch = mTrailerData[position].getKey();
        mTrailerListTextView.setText(trailerToBind);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri openTrailerVideo = Uri.parse(TRAILER_BASE_URL + trailerToWatch);
                Intent trailerIntent = new Intent(Intent.ACTION_VIEW, openTrailerVideo);
                mContext.startActivity(trailerIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mTrailerData) {
            return 0;
        }
        return mTrailerData.length;
    }

}
