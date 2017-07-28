package com.acukanov.sml.data.db;


import com.acukanov.data.model.Results;

import java.util.List;

import io.reactivex.Observable;

public interface DatabaseService {
    Observable<List<Results>> getTopRated();
    Observable<List<Results>> getPopular();
    Observable<List<Results>> getTopRatedWithOffset(int page);
    Observable<List<Results>> getPopularWithOffset(int page);
    Observable<Results> getTopRatedById(int id);
    Observable<Results> getPopularById(int id);
    Observable<List<Long>> saveTopRated(List<Results> results);
    Observable<List<Long>> savePopular(List<Results> results);
    void cleanTopRated();
    void cleanPopular();
}
