package com.acukanov.sml.ui.video.list;


import com.acukanov.data.model.CategoryTypes;
import com.acukanov.data.model.Results;
import com.acukanov.sml.ui.base.BaseView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

public interface IVideoListView extends BaseView {

    void showProgress();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showError(int resMessage, String error);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void addVideos(List<Results> results);

    void setVideos(List<Results> results);

    void changeCategory(CategoryTypes categoryTypes);

    void showInternetConnectionException();
}