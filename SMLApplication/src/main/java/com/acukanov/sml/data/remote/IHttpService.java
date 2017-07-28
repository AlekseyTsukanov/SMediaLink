package com.acukanov.sml.data.remote;


import com.acukanov.data.model.ResponseWrapper;

import io.reactivex.Observable;


public interface IHttpService {
    Observable<ResponseWrapper> getPopular(int page);
    Observable<ResponseWrapper> getTopRated(int page);

}
