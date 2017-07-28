package com.acukanov.data.interactor;


import com.acukanov.data.model.Results;

import java.util.List;

import io.reactivex.Observable;

public interface PopularInteractor {
    Observable<List<Results>> getAllPopular(int page);
    Observable<List<Results>> getPopularWithOffset(int page);
    Observable<Results> getById(int id);
    Observable<List<Results>> requestMorePopular(int page);
    Observable<List<Results>> requestPopular(int page);
}
