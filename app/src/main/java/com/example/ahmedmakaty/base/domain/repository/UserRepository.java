package com.example.ahmedmakaty.base.domain.repository;

import com.example.ahmedmakaty.base.data.model.Article;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface UserRepository {

    Flowable<? extends ArrayList<Article>> getArticles(int page);
}
