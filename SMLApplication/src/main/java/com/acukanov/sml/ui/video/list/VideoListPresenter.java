package com.acukanov.sml.ui.video.list;


import com.acukanov.data.Categories;
import com.acukanov.data.interactor.PopularInteractor;
import com.acukanov.data.interactor.TopRatedInteractor;
import com.acukanov.data.model.CategoryTypes;
import com.acukanov.data.model.Results;
import com.acukanov.sml.SMLApplication;
import com.acukanov.sml.exception.NetworkConnectionException;
import com.acukanov.sml.injection.module.VideoListModule;
import com.acukanov.sml.ui.base.BasePresenter;
import com.acukanov.sml.utils.LogUtils;
import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class VideoListPresenter extends BasePresenter<IVideoListView> {
    private static final String LOG_TAG = LogUtils.makeLogTag(VideoListPresenter.class);
    @Inject PopularInteractor mPopularInteractor;
    @Inject TopRatedInteractor mTopRatedInteractor;
    @Inject Categories mCategories;
    private int mTopRatedPages = 1;
    private int mPopularPages = 1;

    @Inject
    public VideoListPresenter() {
        SMLApplication.getAppComponent().plus(new VideoListModule()).inject(this);
    }

    void getPopular() {
        getViewState().showProgress();
        Observable<List<Results>> observable = mPopularInteractor.getPopularWithOffset(mPopularPages);
        Disposable disposable = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    mCategories.addPopular(results);
                    mPopularPages++;
                    getViewState().addVideos(mCategories.getPopular());
                    getViewState().hideProgress();
                }, throwable -> {
                    getViewState().showError(-1, NetworkConnectionException.getError(throwable).getMessage());
                    getViewState().hideProgress();
                });
        unsubscribeOnDestroy(disposable);
    }

    void requestPopular() {
        mPopularPages = 1;
        getViewState().showProgress();
        Observable<List<Results>> observable = mPopularInteractor.requestPopular(mPopularPages);
        Disposable disposable = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    mCategories.setPopular(results);
                    mPopularPages++;
                    getViewState().setVideos(mCategories.getPopular());
                    getViewState().hideProgress();
                }, throwable -> {
                    getViewState().showError(-1, NetworkConnectionException.getError(throwable).getMessage());
                    getViewState().hideProgress();
                });
        unsubscribeOnDestroy(disposable);
    }

    void getTopRated() {
        getViewState().showProgress();
        Observable<List<Results>> observable = mTopRatedInteractor.getTopRatedWithOffset(mTopRatedPages);
        Disposable disposable = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    mCategories.addTopRated(results);
                    mTopRatedPages++;
                    getViewState().addVideos(mCategories.getTopRated());
                    getViewState().hideProgress();
                }, throwable -> {
                    getViewState().showError(-1, NetworkConnectionException.getError(throwable).getMessage());
                    getViewState().hideProgress();
                });
        unsubscribeOnDestroy(disposable);
    }

    void requestTopRated() {
        mTopRatedPages = 1;
        getViewState().showProgress();
        Observable<List<Results>> observable = mTopRatedInteractor.requestTopRated(mTopRatedPages);
        Disposable disposable = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    mCategories.setTopRated(results);
                    mTopRatedPages++;
                    getViewState().setVideos(mCategories.getTopRated());
                    getViewState().hideProgress();
                }, throwable -> {
                    getViewState().showError(-1, NetworkConnectionException.getError(throwable).getMessage());
                    getViewState().hideProgress();
                });
        unsubscribeOnDestroy(disposable);
    }

    void switchCategoryType(CategoryTypes category) {
        clearSubscriptions(getViewState());
        List<Results> results = null;
        switch (category) {
            case TOP_RATED:
                results = mCategories.getTopRated();
                break;
            case POPULAR:
                results = mCategories.getPopular();
                break;
        }
        if (results == null) {
            switch (category) {
                case TOP_RATED:
                    requestTopRated();
                    break;
                case POPULAR:
                    requestPopular();
                    break;
            }
        } else {
            getViewState().setVideos(results);
        }
        getViewState().changeCategory(category);
    }

}