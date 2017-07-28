package com.acukanov.sml.injection.component;


import android.content.Context;

import com.acukanov.sml.injection.module.ContextModule;
import com.acukanov.sml.injection.module.DataModule;
import com.acukanov.sml.injection.module.VideoDetailsModule;
import com.acukanov.sml.injection.module.VideoListModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ContextModule.class,
        DataModule.class
})
public interface ApplicationComponent {
    Context getContext();
    VideoListComponent plus(VideoListModule videosModule);
    VideoDetailsComponent plus(VideoDetailsModule videoDetailsModule);
}
