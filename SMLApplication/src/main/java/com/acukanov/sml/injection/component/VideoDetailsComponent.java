package com.acukanov.sml.injection.component;


import com.acukanov.sml.injection.module.VideoDetailsModule;
import com.acukanov.sml.injection.scope.ActivityScope;
import com.acukanov.sml.ui.video.details.VideoDetailsFragment;
import com.acukanov.sml.ui.video.details.VideoDetailsPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {VideoDetailsModule.class})
@ActivityScope
public interface VideoDetailsComponent {
    void inject(VideoDetailsFragment videoDetailsFragment);
    void inject(VideoDetailsPresenter videoDetailsPresenter);
}
