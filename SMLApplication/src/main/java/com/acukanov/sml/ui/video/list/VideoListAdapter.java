package com.acukanov.sml.ui.video.list;


import android.app.Activity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acukanov.data.model.Results;
import com.acukanov.sml.BuildConfig;
import com.acukanov.sml.R;
import com.acukanov.sml.utils.LogUtils;
import com.acukanov.sml.utils.ResultsDiffCallback;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {
    private static final String LOG_TAG = LogUtils.makeLogTag(VideoListAdapter.class);
    private Activity mActivity;
    private List<Results> mResults;
    private OnListItemSelectedListener onListItemSelectedListener;
    private OnListChangeListenerListener onListChangeListener;

    public VideoListAdapter(Activity activity) {
        mActivity = activity;
        mResults = new ArrayList<>();
    }

    @Override
    public VideoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoListAdapter.ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.list_item_videos, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoListAdapter.ViewHolder holder, int position) {
        Results videoInfo = mResults.get(position);
        Glide.with(mActivity)
                .load(BuildConfig.IMAGE_BASE_URL + videoInfo.getPosterPath())
                .placeholder(R.drawable.background_overlay)
                .centerCrop()
                .into(holder.mImagePoster);
        holder.mTextTitle.setText(videoInfo.getTitle());
        holder.itemView.setOnClickListener(v -> {
            if (onListItemSelectedListener != null) {
                onListItemSelectedListener.onListItemSelected(videoInfo.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void setOnListItemSelectedListener(OnListItemSelectedListener onListItemSelectedListener) {
        this.onListItemSelectedListener = onListItemSelectedListener;
    }

    public void setOnListChangeListener(OnListChangeListenerListener onListClearedListener) {
        this.onListChangeListener = onListClearedListener;
    }

    public void clearList() {
        mResults.clear();
        notifyDataSetChanged();
        if (onListChangeListener != null) {
            onListChangeListener.onListCleared();
        }
    }

    public void swapItems(List<Results> results) {
        final ResultsDiffCallback diffCallback = new ResultsDiffCallback(this.mResults, results);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.mResults.clear();
        this.mResults.addAll(results);
        diffResult.dispatchUpdatesTo(this);
        if (onListChangeListener != null) {
            onListChangeListener.onListUpdated();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_poster) ImageView mImagePoster;
        @BindView(R.id.text_title) TextView mTextTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}