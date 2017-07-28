package com.acukanov.sml.injection.module;


import android.app.Activity;

import com.acukanov.sml.injection.scope.ActivityScope;
import com.acukanov.sml.ui.video.details.VideoDetailsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoDetailsModule {
    private Activity mActivity;

    public VideoDetailsModule() {}

    @Provides
    @ActivityScope
    public Activity providesActivity() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    VideoDetailsPresenter providesVideosPresenter() {
        return new VideoDetailsPresenter();
    }
}
