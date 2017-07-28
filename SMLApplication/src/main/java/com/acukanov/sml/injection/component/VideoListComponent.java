package com.acukanov.sml.injection.component;


import com.acukanov.sml.injection.module.VideoListModule;
import com.acukanov.sml.injection.scope.ActivityScope;
import com.acukanov.sml.ui.video.list.VideoListFragment;
import com.acukanov.sml.ui.video.list.VideoListPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {VideoListModule.class})
@ActivityScope
public interface VideoListComponent {
    void inject(VideoListFragment testFragment);
    void inject(VideoListPresenter testPresenter);
}
