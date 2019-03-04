package com.example.ahmedmakaty.base.injection.module.screens;

import com.example.ahmedmakaty.base.presentation.screens.ArticleDetails.ArticleDetailsViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ArticleDetailsScreenModule {

    @Provides
    ArticleDetailsViewModelFactory providesArticleDetailsViewModelFactory() {
        return new ArticleDetailsViewModelFactory();
    }
}