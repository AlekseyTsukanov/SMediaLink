package com.acukanov.sml.utils;


import android.support.v7.util.DiffUtil;

import com.acukanov.data.model.Results;

import java.util.List;

import javax.annotation.Nullable;

public class ResultsDiffCallback extends DiffUtil.Callback{
    private final List<Results> oldList;
    private final List<Results> newList;

    public ResultsDiffCallback(List<Results> oldList, List<Results> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Results oldItem = oldList.get(oldItemPosition);
        final Results newItem = newList.get(newItemPosition);
        return oldItem.getId() == newItem.getId();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
