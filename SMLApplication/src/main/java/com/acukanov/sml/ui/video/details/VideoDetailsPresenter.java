package com.acukanov.sml.ui.video.details;


import com.acukanov.data.interactor.PopularInteractor;
import com.acukanov.data.interactor.TopRatedInteractor;
import com.acukanov.data.model.Results;
import com.acukanov.sml.SMLApplication;
import com.acukanov.sml.injection.module.VideoDetailsModule;
import com.acukanov.sml.ui.base.BasePresenter;
import com.acukanov.sml.utils.LogUtils;
import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class VideoDetailsPresenter extends BasePresenter<IVideoDetailsView> {
    private static final String LOG_TAG = LogUtils.makeLogTag(VideoDetailsPresenter.class);
    @Inject PopularInteractor mPopularInteractor;
    @Inject TopRatedInteractor mTopRatedInteractor;

    public VideoDetailsPresenter() {
        SMLApplication.getAppComponent().plus(new VideoDetailsModule()).inject(this);
    }

    void getTopRatedById(int id) {
        getViewState().showProgress();
        Observable<Results> observable = mTopRatedInteractor.getById(id);
        Disposable disposable = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    getViewState().showResult(result);
                    getViewState().hideProgress();
                }, throwable -> {
                    getViewState().showError(-1, throwable.getMessage());
                    getViewState().hideProgress();
                });
        unsubscribeOnDestroy(disposable);
    }

    void getPopularById(int id) {
        getViewState().showProgress();
        Observable<Results> observable = mPopularInteractor.getById(id);
        Disposable disposable = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    getViewState().showResult(result);
                    getViewState().hideProgress();
                }, throwable -> {
                    getViewState().showError(-1, throwable.getMessage());
                    getViewState().hideProgress();
                });
        unsubscribeOnDestroy(disposable);
    }
}
