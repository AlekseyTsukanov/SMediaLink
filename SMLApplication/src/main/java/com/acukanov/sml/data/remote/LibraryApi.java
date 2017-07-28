package com.acukanov.sml.data.remote;


import com.acukanov.data.model.ResponseWrapper;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LibraryApi {
    @GET("3/movie/popular")
    Observable<ResponseWrapper> getPopular(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("3/movie/top_rated")
    Observable<ResponseWrapper> getTopRated(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}
