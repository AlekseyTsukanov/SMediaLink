package com.acukanov.sml.data.repository;


import com.acukanov.data.interactor.PopularInteractor;
import com.acukanov.data.model.ResponseWrapper;
import com.acukanov.data.model.Results;
import com.acukanov.sml.data.db.DatabaseService;
import com.acukanov.sml.data.remote.IHttpService;
import com.acukanov.sml.utils.LogUtils;

import java.util.List;

import io.reactivex.Observable;

public class PopularImpl implements PopularInteractor {
    private static final String LOG_TAG = LogUtils.makeLogTag(PopularImpl.class);
    private static volatile PopularImpl sInstance;
    private IHttpService mHttp;
    private DatabaseService mDb;

    private PopularImpl(IHttpService http, DatabaseService db) {
        mHttp = http;
        mDb = db;
    }

    public static PopularImpl getInstance(IHttpService http, DatabaseService db) {
        if (sInstance == null) {
            synchronized (PopularImpl.class) {
                if (sInstance == null) {
                    sInstance = new PopularImpl(http, db);
                }
            }
        }
        return sInstance;
    }

    @Override
    public Observable<List<Results>> getAllPopular(int page) {
        return mDb.getPopular()
                .flatMap(results -> {
                    if (results != null && !results.isEmpty()) {
                        return Observable.just(results);
                    } else {
                        return requestMorePopular(page);
                    }
                });
    }

    @Override
    public Observable<List<Results>> getPopularWithOffset(int page) {
        return mDb.getPopularWithOffset(page)
                .flatMap(results -> {
                    if (results != null && !results.isEmpty()) {
                        return Observable.just(results);
                    } else {
                        return requestMorePopular(page);
                    }
                });
    }

    @Override
    public Observable<Results> getById(int id) {
        return mDb.getPopularById(id);
    }

    @Override
    public Observable<List<Results>> requestMorePopular(int page) {
        return mHttp.getPopular(page)
                .map(ResponseWrapper::getResults)
                .doOnNext(results -> {
                    mDb.savePopular(results).subscribe(ids -> {
                        LogUtils.debug(LOG_TAG, "Popular saved: " + ids.size());
                    }, throwable -> {
                        LogUtils.error(LOG_TAG, throwable.getMessage());
                    });
                });
    }

    @Override
    public Observable<List<Results>> requestPopular(int page) {
        return mHttp.getPopular(page)
                .map(ResponseWrapper::getResults)
                .doOnNext(results -> {
                    mDb.cleanPopular();
                    mDb.savePopular(results).subscribe(ids -> {
                        LogUtils.debug(LOG_TAG, "Popular saved: " + ids.size());
                    }, throwable -> {
                        LogUtils.error(LOG_TAG, throwable.getMessage());
                    });
                });
    }
}
