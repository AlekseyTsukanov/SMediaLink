package com.acukanov.sml;


import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.acukanov.sml.injection.component.ApplicationComponent;
import com.acukanov.sml.injection.component.DaggerApplicationComponent;
import com.acukanov.sml.injection.module.ContextModule;

import butterknife.ButterKnife;

public class SMLApplication extends Application {
    private static ApplicationComponent sApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(true);
        sApplicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }


    public static ApplicationComponent getAppComponent() {
        return sApplicationComponent;
    }

    @VisibleForTesting
    public static void setAppComponent(@NonNull ApplicationComponent appComponent) {
        sApplicationComponent = appComponent;
    }
}
