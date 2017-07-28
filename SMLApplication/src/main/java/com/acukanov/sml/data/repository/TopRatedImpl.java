package com.acukanov.sml.data.repository;


import com.acukanov.data.interactor.TopRatedInteractor;
import com.acukanov.data.model.ResponseWrapper;
import com.acukanov.data.model.Results;
import com.acukanov.sml.data.db.DatabaseService;
import com.acukanov.sml.data.remote.IHttpService;
import com.acukanov.sml.utils.LogUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


public class TopRatedImpl implements TopRatedInteractor {
    private static final String LOG_TAG = LogUtils.makeLogTag(TopRatedImpl.class);
    private static volatile TopRatedImpl sInstance;
    private IHttpService mHttp;
    private DatabaseService mDb;

    private TopRatedImpl(IHttpService http, DatabaseService db) {
        mHttp = http;
        mDb = db;
    }

    public static TopRatedImpl getInstance(IHttpService http, DatabaseService db) {
        if (sInstance == null) {
            synchronized (TopRatedImpl.class) {
                if (sInstance == null) {
                    sInstance = new TopRatedImpl(http, db);
                }
            }
        }
        return sInstance;
    }

    @Override
    public Observable<List<Results>> getAllTopRated(int page) {
        return mDb.getTopRated()
                .flatMap(results -> {
                    if (results != null && !results.isEmpty()) {
                        return Observable.just(results);
                    } else {
                        return requestMoreTopRated(page);
                    }
                });
    }

    @Override
    public Observable<List<Results>> getTopRatedWithOffset(int page) {
        return mDb.getTopRatedWithOffset(page)
                .flatMap(results -> {
                    if (results != null && !results.isEmpty()) {
                        return Observable.just(results);
                    } else {
                        return requestMoreTopRated(page);
                    }
                });
    }

    @Override
    public Observable<Results> getById(int id) {
        return mDb.getTopRatedById(id);
    }

    @Override
    public Observable<List<Results>> requestMoreTopRated(int page) {
        return mHttp.getTopRated(page)
                .map(ResponseWrapper::getResults)
                .doOnNext(results -> {
                    mDb.saveTopRated(results).subscribe(ids -> {
                        LogUtils.debug(LOG_TAG, "Top rated saved: " + ids.size());
                    }, throwable -> {
                        LogUtils.error(LOG_TAG, throwable.getMessage());
                    });
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.error(LOG_TAG, "asd");
                    }
                });
    }

    @Override
    public Observable<List<Results>> requestTopRated(int page) {
        return mHttp.getTopRated(page)
                .map(ResponseWrapper::getResults)
                .doOnNext(results -> {
                    mDb.cleanTopRated();
                    mDb.saveTopRated(results).subscribe(ids -> {
                        LogUtils.debug(LOG_TAG, "Top rated saved: " + ids.size());
                    }, throwable -> {
                        LogUtils.error(LOG_TAG, throwable.getMessage());
                    });
                });
    }
}
