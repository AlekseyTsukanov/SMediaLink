package com.acukanov.sml.ui.video.list;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.acukanov.data.model.CategoryTypes;
import com.acukanov.data.model.Results;
import com.acukanov.sml.R;
import com.acukanov.sml.SMLApplication;
import com.acukanov.sml.injection.module.VideoListModule;
import com.acukanov.sml.ui.base.BaseFragment;
import com.acukanov.sml.ui.video.details.VideoDetailsActivity;
import com.acukanov.sml.utils.ActivityCommon;
import com.acukanov.sml.utils.LogUtils;
import com.acukanov.sml.utils.PermissionsUtils;
import com.acukanov.sml.view.RecyclerViewScrollListener;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListFragment extends BaseFragment implements IVideoListView, OnListItemSelectedListener, OnListChangeListenerListener {
    private static final String LOG_TAG = LogUtils.makeLogTag(VideoListFragment.class);
    private static final int PERMISSION_INTERNET_REQUEST_CODE = 0;
    private Activity mActivity;
    @InjectPresenter VideoListPresenter mPresenter;
    @Inject VideoListAdapter mAdapter;
    @BindView(R.id.swipe_refresh_container) SwipeRefreshLayout mRefreshContainer;
    @BindView(R.id.videos_list) RecyclerView mVideosList;
    @BindView(R.id.progress) ProgressBar mProgress;
    @BindView(R.id.fab_scroll_top) FloatingActionButton mBtnScrollTop;
    private GridLayoutManager mLayoutManager;
    private RecyclerViewScrollListener mScrollListener;
    private CategoryTypes mCurrentType;

    public VideoListFragment() {}

    public static VideoListFragment newInstance() {
        return new VideoListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_video_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_show_top_rated:
                if (mCurrentType != CategoryTypes.TOP_RATED) {
                    mPresenter.switchCategoryType(CategoryTypes.TOP_RATED);
                    setTitle(R.string.title_top_rated);
                }
                return true;
            case R.id.menu_action_show_popular:
                if (mCurrentType != CategoryTypes.POPULAR) {
                    mPresenter.switchCategoryType(CategoryTypes.POPULAR);
                    setTitle(R.string.title_popular);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMLApplication.getAppComponent().plus(new VideoListModule(mActivity)).inject(this);
        setHasOptionsMenu(true);
        mCurrentType = CategoryTypes.TOP_RATED;
        mAdapter.setOnListItemSelectedListener(this);
        mAdapter.setOnListChangeListener(this);
        mLayoutManager = new GridLayoutManager(mActivity, getResources().getInteger(R.integer.span_count));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mVideosList.setLayoutManager(mLayoutManager);
        mVideosList.setAdapter(mAdapter);
        mVideosList.addOnScrollListener(new RecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onPageChanged(RecyclerViewScrollListener instance, int page) {
                if (mScrollListener == null) {
                    mScrollListener = instance;
                }
                switch (mCurrentType) {
                    case TOP_RATED:
                        mPresenter.getTopRated();
                        break;
                    case POPULAR:
                        mPresenter.getPopular();
                        break;
                }
            }

            @Override
            public void showScrollTop() {
                mBtnScrollTop.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideScrollTop() {
                mBtnScrollTop.setVisibility(View.GONE);
            }
        });
        if (savedInstanceState == null) {
            mPresenter.getTopRated();
        }
        mRefreshContainer.setOnRefreshListener(() -> {
            //if (!NetworkUtils.isNetworkAvailable(mActivity)) {
                //showError(R.string.dialog_message_no_internet_connection, null);
            //} else {
                switch (mCurrentType) {
                    case TOP_RATED:
                        mPresenter.requestTopRated();
                        break;
                    case POPULAR:
                        mPresenter.requestPopular();
                        break;
                }
            //}
            mRefreshContainer.setRefreshing(false);
        });

        mBtnScrollTop.setOnClickListener(v -> {
            mVideosList.scrollToPosition(0);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!PermissionsUtils.hasPermission(mActivity, Manifest.permission.INTERNET)) {
            PermissionsUtils.requestPermissionsSafely(
                    mActivity,
                    this,
                    new String[] {Manifest.permission.INTERNET}, PERMISSION_INTERNET_REQUEST_CODE
            );
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (mCurrentType) {
            case POPULAR:
                setTitle(R.string.title_popular);
                break;
            case TOP_RATED:
                setTitle(R.string.title_top_rated);
                break;
        }
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError(int resMessage, String error) {
        ActivityCommon.showError(mActivity, resMessage, error);
        //enablePagination();
    }

    @Override
    public void addVideos(List<Results> results) {
        mAdapter.swapItems(results);
    }

    @Override
    public void setVideos(List<Results> results) {
        mAdapter.clearList();
        mAdapter.swapItems(results);
    }

    @Override
    public void changeCategory(CategoryTypes categoryTypes) {
        mCurrentType = categoryTypes;
    }

    @Override
    public void showInternetConnectionException() {

    }

    @Override
    public void onListItemSelected(int id) {
        VideoDetailsActivity.startActivity(mActivity, this, id, mCurrentType);
    }

    @Override
    public void onListCleared() {
        if (mScrollListener != null) {
            mScrollListener.releasePages();
        }
    }

    @Override
    public void onListUpdated() {
        enablePagination();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_INTERNET_REQUEST_CODE:
                switch(mCurrentType) {
                    case TOP_RATED:
                        mPresenter.getTopRated();
                        break;
                    case POPULAR:
                        mPresenter.getPopular();
                        break;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void enablePagination() {
        if (mScrollListener != null) {
            mScrollListener.setLoading(true);
        }
    }
}
