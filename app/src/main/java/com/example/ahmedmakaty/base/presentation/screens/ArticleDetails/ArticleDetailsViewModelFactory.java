package com.example.ahmedmakaty.base.presentation.screens.ArticleDetails;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class ArticleDetailsViewModelFactory implements ViewModelProvider.Factory{

    @Inject
    public ArticleDetailsViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArticleDetailsViewModel.class)) {
            return (T) new ArticleDetailsViewModel();
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}