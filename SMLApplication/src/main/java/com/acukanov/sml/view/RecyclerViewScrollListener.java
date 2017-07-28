package com.acukanov.sml.view;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.acukanov.sml.utils.LogUtils;

public abstract class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private static final String LOG_TAG = LogUtils.makeLogTag(RecyclerViewScrollListener.class);
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private int lastPage = 1;
    private boolean isLoading = true;
    private boolean isScrollTopShown = false;
    private GridLayoutManager mManager;

    public RecyclerViewScrollListener(GridLayoutManager manager) {
        mManager = manager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            visibleItemCount = mManager.getChildCount();
            totalItemCount = mManager.getItemCount();
            pastVisibleItems = mManager.findFirstVisibleItemPosition();
            if (isLoading) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    lastPage++;
                    isLoading = false;
                    onPageChanged(this, lastPage);
                }
            }
            if (!isScrollTopShown) {
                showScrollTop();
                isScrollTopShown = true;
            }
        } else {
            if (isScrollTopShown) {
                hideScrollTop();
                isScrollTopShown = false;
            }
        }
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void releasePages() {
        lastPage = 1;
    }

    public void setPage(int page) {
        lastPage = page;
    }

    public abstract void onPageChanged(RecyclerViewScrollListener instance, int page);

    public abstract void showScrollTop();

    public abstract void hideScrollTop();
}
