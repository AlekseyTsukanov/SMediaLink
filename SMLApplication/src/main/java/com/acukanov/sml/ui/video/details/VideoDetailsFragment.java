package com.acukanov.sml.ui.video.details;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.acukanov.data.model.CategoryTypes;
import com.acukanov.data.model.Results;
import com.acukanov.sml.BuildConfig;
import com.acukanov.sml.R;
import com.acukanov.sml.SMLApplication;
import com.acukanov.sml.injection.module.VideoDetailsModule;
import com.acukanov.sml.ui.base.BaseFragment;
import com.acukanov.sml.utils.ActivityCommon;
import com.acukanov.sml.utils.LogUtils;
import com.acukanov.sml.utils.StringUtils;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoDetailsFragment extends BaseFragment implements IVideoDetailsView {
    private static final String LOG_TAG = LogUtils.makeLogTag(VideoDetailsFragment.class);
    private static final String ARG_VIDEO_ID = "arg_video_id";
    private static final String ARG_VIDEO_TYPE = "arg_video_type";
    private Activity mActivity;
    @InjectPresenter VideoDetailsPresenter mPresenter;
    @BindView(R.id.progress_container) FrameLayout mProgress;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.text_layout_title) TextView mTextVideoTitle;
    @BindView(R.id.text_rating) TextView mTextRating;
    @BindView(R.id.text_date) TextView mTextDate;
    @BindView(R.id.image_placeholder) ImageView mImagePlaceHolder;
    @BindView(R.id.image_poster) ImageView mImagePoster;
    @BindView(R.id.text_description) TextView mTextDescription;
    @BindView(R.id.collapse_toolbar) CollapsingToolbarLayout mCollapseToolbar;
    @BindView(R.id.btn_share) FloatingActionButton mBtnShare;
    private int mVideoId;
    private CategoryTypes mType;

    public VideoDetailsFragment() {}

    public static VideoDetailsFragment newInstance(int id, CategoryTypes type) {
        VideoDetailsFragment instance = new VideoDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VIDEO_ID, id);
        args.putSerializable(ARG_VIDEO_TYPE, type);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMLApplication.getAppComponent().plus(new VideoDetailsModule()).inject(this);
        mVideoId = getArguments().getInt(ARG_VIDEO_ID);
        mType = (CategoryTypes) getArguments().getSerializable(ARG_VIDEO_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ActivityCommon.setUpActionBarToolbar((AppCompatActivity) mActivity, mToolbar).setNavigationOnClickListener(v -> {
            mActivity.onBackPressed();
        });
        ActivityCommon.setHomeAsUp((AppCompatActivity) mActivity);
        switch (mType) {
            case POPULAR:
                mPresenter.getPopularById(mVideoId);
                break;
            case TOP_RATED:
                mPresenter.getTopRatedById(mVideoId);
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
    public void showError(int resMessage, String throwableMessage) {
        ActivityCommon.showError(mActivity, resMessage, throwableMessage);
    }

    @Override
    public void showResult(Results result) {
        mCollapseToolbar.setTitle(result.getTitle());
        mTextVideoTitle.setText(StringUtils.formatToString(mActivity, R.string.text_title, result.getTitle()));
        mTextRating.setText(StringUtils.formatToString(mActivity, R.string.text_rating, String.valueOf(result.getVoteAverage())));
        mTextDate.setText(StringUtils.formatToString(mActivity, R.string.text_date, StringUtils.dateToString(result.getReleaseDate())));
        Glide.with(mActivity)
                .load(BuildConfig.IMAGE_BASE_URL + result.getBackdropPath())
                .placeholder(R.drawable.background_overlay)
                .fitCenter()
                .into(mImagePlaceHolder);
        Glide.with(mActivity)
                .load(BuildConfig.IMAGE_BASE_URL + result.getPosterPath())
                .placeholder(R.drawable.background_overlay)
                .into(mImagePoster);
        mTextDescription.setText(result.getOverview());
        mBtnShare.setVisibility(View.VISIBLE);
        mBtnShare.setOnClickListener(v -> {
            ActivityCommon.startShareIntent(mActivity, this, result.getTitle());
        });
    }
}
