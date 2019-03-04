package com.example.ahmedmakaty.base.presentation.screens.ArticleDetails;

import android.arch.lifecycle.ViewModel;

import com.example.ahmedmakaty.base.data.model.Article;

public class ArticleDetailsViewModel extends ViewModel {

    private Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
