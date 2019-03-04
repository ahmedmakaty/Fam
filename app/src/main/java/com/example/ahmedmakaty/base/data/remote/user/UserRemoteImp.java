package com.example.ahmedmakaty.base.data.remote.user;

import com.example.ahmedmakaty.base.data.Constants;
import com.example.ahmedmakaty.base.data.model.Article;
import com.example.ahmedmakaty.base.data.remote.ApiServiceInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class UserRemoteImp implements UserRemote {

    private ApiServiceInterface apiServiceInterface;

    @Inject
    public UserRemoteImp(ApiServiceInterface apiServiceInterface) {
        this.apiServiceInterface = apiServiceInterface;
    }

    @Override
    public Flowable<? extends ArrayList<Article>> getArticles(int page) {
        return apiServiceInterface.getArticles("league of legends", Constants.NEWS_API_KEY, 15, page).map(response ->
                (ArrayList<Article>) response.getArticles());
    }
}
