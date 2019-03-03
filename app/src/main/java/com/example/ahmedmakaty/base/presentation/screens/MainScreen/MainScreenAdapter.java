package com.example.ahmedmakaty.base.presentation.screens.MainScreen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmedmakaty.base.R;
import com.example.ahmedmakaty.base.data.model.Article;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class MainScreenAdapter extends RecyclerView.Adapter<MainScreenAdapter.ArticleViewHolder> {

    ArrayList<Article> articles = new ArrayList();

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        Article article = articles.get(position);

    }

    public void setData(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
