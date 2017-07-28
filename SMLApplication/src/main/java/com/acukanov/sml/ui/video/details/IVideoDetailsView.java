package com.acukanov.sml.ui.video.details;


import com.acukanov.data.model.Results;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface IVideoDetailsView extends MvpView {

    void showProgress();

    void hideProgress();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showError(int resMessage, String throwableMessage);

    void showResult(Results result);
}
