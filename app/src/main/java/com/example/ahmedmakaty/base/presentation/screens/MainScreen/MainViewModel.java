package com.example.ahmedmakaty.base.presentation.screens.MainScreen;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ahmedmakaty.base.data.model.Article;
import com.example.ahmedmakaty.base.domain.interactor.GetArticlesUseCase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

public class MainViewModel extends ViewModel {

    GetArticlesUseCase getArticlesUseCase;

    MutableLiveData<ArrayList<Article>> articlesSLD = new MutableLiveData<>();
    ArrayList<Article> articles = new ArrayList<>();
    MutableLiveData<Boolean> loadMoreSLD = new MutableLiveData<>();
    MutableLiveData<Boolean> progressSLD = new MutableLiveData<>();
    MutableLiveData<String> apiErrorSLD = new MutableLiveData<>();

    int page = 1;
    boolean loading = false;
    boolean fullyLoaded = false;

    public MainViewModel(GetArticlesUseCase getArticlesUseCase) {
        this.getArticlesUseCase = getArticlesUseCase;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

    }

    public void getFeed() {
        loading = true;
        if (page == 1) {
            progressSLD.postValue(true);
        }
        getArticlesUseCase.execute(new DisposableSubscriber<ArrayList<Article>>() {
            @Override
            public void onNext(ArrayList<Article> arts) {
                loading = false;
                if (page == 1) {
                    progressSLD.postValue(false);
                } else {
                    loadMoreSLD.postValue(loading);
                }
                if (arts.size() < 10) {
                    fullyLoaded = true;
                }

                for (Article a : arts) {
                    articles.add(a);
                }
                articlesSLD.postValue(articles);
            }

            @Override
            public void onError(Throwable t) {
                apiErrorSLD.postValue("An error has occurred");
                loading = false;
                if (page == 1) {
                    progressSLD.postValue(false);
                } else {
                    loadMoreSLD.postValue(loading);
                }
            }

            @Override
            public void onComplete() {

            }
        }, page);
    }

    public void loadNext() {
        if (!loading && !fullyLoaded) {
            loading = true;
            loadMoreSLD.postValue(loading);
        }
    }

    public void addDummyLoadingObjectToList() {
        articles.add(null);
        articlesSLD.postValue(articles);
    }

    public void removeDummyObjectFromList() {
        articles.removeAll(Collections.singleton(null));
        articlesSLD.postValue(articles);
    }

    public void resetData() {
        loading = false;
        fullyLoaded = false;
        articles.clear();
        page = 1;
    }
}
