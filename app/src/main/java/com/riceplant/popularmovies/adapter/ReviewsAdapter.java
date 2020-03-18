package com.riceplant.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riceplant.popularmovies.R;
import com.riceplant.popularmovies.model.Reviews;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder> {

    private Reviews[] mReviewData;
    public static TextView mAuthorListTextView = null;
    public static TextView mContentListTextView = null;
    Context mContext;

    public ReviewsAdapter(Reviews[] reviewData, Context context) {
        mReviewData = reviewData;
        mContext = context;
    }

    @NonNull
    @Override
    public ReviewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForIdListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForIdListItem, parent, shouldAttachToParentImmediately);
        return new ReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapterViewHolder holder, int position) {
        String authorToBind = mReviewData[position].getAuthor();
        String contentToBind = mReviewData[position].getContent();

        mAuthorListTextView.setText(authorToBind);
        mContentListTextView.setText(contentToBind);
    }

    @Override
    public int getItemCount() {
        if (null == mReviewData) {
            return 0;
        }
        return mReviewData.length;
    }

    public class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder{

        public ReviewsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthorListTextView = (TextView) itemView.findViewById(R.id.tv_review_author);
            mContentListTextView = (TextView) itemView.findViewById(R.id.tv_review_content);
        }
    }
}
