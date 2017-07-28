package com.acukanov.sml.injection.module;


import android.content.Context;

import com.acukanov.data.interactor.PopularInteractor;
import com.acukanov.data.interactor.TopRatedInteractor;
import com.acukanov.data.Categories;
import com.acukanov.sml.data.db.DatabaseHelper;
import com.acukanov.sml.data.db.DatabaseService;
import com.acukanov.sml.data.remote.IHttpService;
import com.acukanov.sml.data.remote.LibraryService;
import com.acukanov.sml.data.repository.PopularImpl;
import com.acukanov.sml.data.repository.TopRatedImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
public class DataModule {

    @Provides
    @Singleton
    DatabaseService providesDatabaseService(Context context) {
        return new DatabaseHelper(context, Schedulers.io());
    }

    @Provides
    @Singleton
    IHttpService providesHttpService() {
        return new LibraryService();
    }

    @Provides
    PopularInteractor providesPopularInteractor(IHttpService http, DatabaseService db) {
        return PopularImpl.getInstance(http, db);
    }

    @Provides
    TopRatedInteractor providesTopRatedInteractor(IHttpService http, DatabaseService db) {
        return TopRatedImpl.getInstance(http, db);
    }

    @Provides
    Categories providesCategories() {
        return new Categories();
    }
}
