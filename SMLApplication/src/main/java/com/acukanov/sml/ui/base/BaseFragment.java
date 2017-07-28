package com.acukanov.sml.ui.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpDelegate;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseFragment extends Fragment {
    private MvpDelegate<? extends MvpAppCompatFragment> mMvpDelegate;
    private Activity mActivity;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BaseFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.getMvpDelegate().onAttach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.getMvpDelegate().onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        boolean anyParentIsRemoving = false;

        for (Fragment parent = this.getParentFragment(); !anyParentIsRemoving && parent != null; parent = parent.getParentFragment()) {
            anyParentIsRemoving = parent.isRemoving();
        }

        if (this.isRemoving() || anyParentIsRemoving || this.getActivity().isFinishing()) {
            this.getMvpDelegate().onDestroy();
        }
        if (compositeDisposable.size() > 0) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.getMvpDelegate().onSaveInstanceState(outState);
    }

    public MvpDelegate getMvpDelegate() {
        if (this.mMvpDelegate == null) {
            this.mMvpDelegate = new MvpDelegate(this);
        }
        return this.mMvpDelegate;
    }

    public void setEmptyTitle() {
        setTitle("");
    }

    public void setTitle(String title) {
        ActionBar actionBar = ((BaseActivity) mActivity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setTitle(@StringRes int resId) {
        setTitle(getActivity().getString(resId));
    }

    public String getTitle() {
        String title = null;
        ActionBar actionBar = ((BaseActivity) mActivity).getSupportActionBar();
        if (actionBar != null) {
            title = (String) actionBar.getTitle();
        }
        return title;
    }

    public void unsubscribeOnDestroy(Disposable subscription) {
        compositeDisposable.add(subscription);
    }
}
