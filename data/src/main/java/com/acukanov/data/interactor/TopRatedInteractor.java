package com.acukanov.data.interactor;


import com.acukanov.data.model.Results;

import java.util.List;

import io.reactivex.Observable;


public interface TopRatedInteractor {
    Observable<List<Results>> getAllTopRated(int page);
    Observable<List<Results>> getTopRatedWithOffset(int page);
    Observable<Results> getById(int id);
    Observable<List<Results>> requestMoreTopRated(int page);
    Observable<List<Results>> requestTopRated(int page);
}
