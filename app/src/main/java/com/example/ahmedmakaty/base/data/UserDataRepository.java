package com.example.ahmedmakaty.base.data;


import com.example.ahmedmakaty.base.data.cache.user.UserCache;
import com.example.ahmedmakaty.base.data.model.Article;
import com.example.ahmedmakaty.base.data.remote.user.UserRemote;
import com.example.ahmedmakaty.base.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class UserDataRepository implements UserRepository {

    private UserCache userCache;
    private UserRemote userRemote;

    @Inject
    public UserDataRepository(UserCache userCache, UserRemote userRemote) {
        this.userCache = userCache;
        this.userRemote = userRemote;
    }

    @Override
    public Flowable<? extends ArrayList<Article>> getArticles(int page) {
        return userRemote.getArticles(page);
    }
}
