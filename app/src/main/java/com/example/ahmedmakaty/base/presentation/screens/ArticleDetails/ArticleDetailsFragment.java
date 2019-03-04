package com.example.ahmedmakaty.base.presentation.screens.ArticleDetails;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmedmakaty.base.R;
import com.example.ahmedmakaty.base.data.Constants;
import com.example.ahmedmakaty.base.data.model.Article;
import com.example.ahmedmakaty.base.presentation.BaseActivity;
import com.example.ahmedmakaty.base.presentation.BaseFragment;
import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class ArticleDetailsFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTitle)
    TextView title;

    @Inject
    ArticleDetailsViewModelFactory articleDetailsViewModelFactory;

    ArticleDetailsViewModel articleDetailsViewModel;

    public static ArticleDetailsFragment newInstance(String articleModel) {

        Bundle args = new Bundle();

        ArticleDetailsFragment fragment = new ArticleDetailsFragment();
        args.putString(Constants.ARTICLE_MODEL, articleModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);

        articleDetailsViewModel = ViewModelProviders.of(this, articleDetailsViewModelFactory).get(ArticleDetailsViewModel.class);

        articleDetailsViewModel.setArticle(new Gson().fromJson(getArguments().getString(Constants.ARTICLE_MODEL), Article.class));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_details, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeToolbar();
    }

    private void initializeToolbar() {
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        ((BaseActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitle(getResources().getString(R.string.main_title));
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.main_title));
        title.setText("Article");
        ((BaseActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((BaseActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
    }
}
