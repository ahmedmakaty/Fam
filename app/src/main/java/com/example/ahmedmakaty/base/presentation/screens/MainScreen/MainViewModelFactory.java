package com.example.ahmedmakaty.base.presentation.screens.MainScreen;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.ahmedmakaty.base.domain.interactor.GetArticlesUseCase;

import javax.inject.Inject;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    GetArticlesUseCase getArticlesUseCase;

    @Inject
    public MainViewModelFactory(GetArticlesUseCase getArticlesUseCase) {
        this.getArticlesUseCase = getArticlesUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(getArticlesUseCase);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
