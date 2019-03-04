package com.example.ahmedmakaty.base.injection.module.screens;

import com.example.ahmedmakaty.base.domain.interactor.GetArticlesUseCase;
import com.example.ahmedmakaty.base.presentation.screens.MainScreen.MainViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenModule {

    @Provides
    MainViewModelFactory providesMainViewModelFactory(GetArticlesUseCase getArticlesUseCase) {
        return new MainViewModelFactory(getArticlesUseCase);
    }
}
