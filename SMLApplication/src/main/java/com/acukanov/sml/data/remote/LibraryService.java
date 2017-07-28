package com.acukanov.sml.data.remote;


import com.acukanov.data.model.ResponseWrapper;
import com.acukanov.sml.BuildConfig;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LibraryService implements IHttpService {
    private static final String LANGUAGE = "en-US";
    private LibraryApi mRequestInterface;

    public LibraryService() {
        // inject
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(provideGsonBuilder()))
                .client(buildOkHttpClient())
                .baseUrl(BuildConfig.SERVER_URL)
                .build();
        mRequestInterface = retrofit.create(LibraryApi.class);
    }

    private OkHttpClient buildOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient.Builder httClientBuilder = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(logging);
        return httClientBuilder.build();
    }

    private Gson provideGsonBuilder() {
        return new GsonBuilder()
                .setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setFieldNamingStrategy(new FieldNamingPolicy())
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                .setLenient()
                .create();
    }

    @Override
    public Observable<ResponseWrapper> getPopular(int page) {
        return mRequestInterface.getPopular(BuildConfig.MOVIE_DB_KEY, LANGUAGE, page);
    }

    @Override
    public Observable<ResponseWrapper> getTopRated(int page) {
        return mRequestInterface.getTopRated(BuildConfig.MOVIE_DB_KEY, LANGUAGE, page);
    }

    private class FieldNamingPolicy implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            String name = com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
            name = name.substring(2, name.length()).toLowerCase();
            return name;
        }
    }
}
