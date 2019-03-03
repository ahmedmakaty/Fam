package com.example.ahmedmakaty.base.data.remote.user;

import com.example.ahmedmakaty.base.data.model.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface UserRemote {

    Flowable<? extends ArrayList<Article>> getArticles();
}
