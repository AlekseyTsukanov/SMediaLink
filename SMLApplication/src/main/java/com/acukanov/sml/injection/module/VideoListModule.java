package com.acukanov.sml.injection.module;


import android.app.Activity;

import com.acukanov.sml.injection.scope.ActivityScope;
import com.acukanov.sml.ui.video.list.VideoListAdapter;
import com.acukanov.sml.ui.video.list.VideoListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoListModule {
    private Activity mActivity;

    public VideoListModule() {}

    public VideoListModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity providesActivity() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    VideoListAdapter providesTestAdapter(Activity activity) {
        return new VideoListAdapter(activity);
    }

    @Provides
    @ActivityScope
    VideoListPresenter providesTestPresenter() {
        return new VideoListPresenter();
    }
}
