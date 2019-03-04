package com.example.ahmedmakaty.base.data.remote;

import com.example.ahmedmakaty.base.data.model.Article;
import com.example.ahmedmakaty.base.data.model.ArticlesResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServiceInterface {

    @GET("https://newsapi.org/v2/everything")
    Flowable<ArticlesResponse> getArticles(@Query("q") String query, @Query("apiKey") String apiKey, @Query("pageSize") int pageSize, @Query("page") int page);
}
