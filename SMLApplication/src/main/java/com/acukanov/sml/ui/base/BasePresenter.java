package com.acukanov.sml.ui.base;


import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void unsubscribeOnDestroy(@NonNull Disposable subscription) {
        compositeDisposable.add(subscription);
    }

    protected void clearSubscriptions(BaseView mvpView) {
        compositeDisposable.clear();
        if (mvpView != null) {
            mvpView.hideProgress();
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
