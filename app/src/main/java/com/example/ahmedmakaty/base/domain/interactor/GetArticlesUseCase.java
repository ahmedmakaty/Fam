package com.example.ahmedmakaty.base.domain.interactor;

import com.example.ahmedmakaty.base.data.UserDataRepository;
import com.example.ahmedmakaty.base.data.model.Article;
import com.example.ahmedmakaty.base.domain.exception.NoInternetConnectionException;
import com.example.ahmedmakaty.base.domain.executer.PostExecutionThread;
import com.example.ahmedmakaty.base.domain.executer.ThreadExecutor;
import com.example.ahmedmakaty.base.domain.helper.FlowableUseCase;
import com.example.ahmedmakaty.base.domain.repository.UserRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetArticlesUseCase extends FlowableUseCase<ArrayList<Article>, Integer> {
    InternetConnectionUseCase internetConnectionUseCase;
    UserRepository userRepository;

    @Inject
    public GetArticlesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, InternetConnectionUseCase internetConnectionUseCase, UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.internetConnectionUseCase = internetConnectionUseCase;
        this.userRepository = userRepository;
    }

    @Override
    protected Flowable<ArrayList<Article>> buildUseCaseObservable(Integer page) {
        return internetConnectionUseCase.buildUseCaseObservable(null).switchMap(connected->{
            if (connected) {
                return userRepository.getArticles(page);
            } else{
                return Flowable.error(new NoInternetConnectionException());
            }
        });
    }
}
