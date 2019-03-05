package com.example.ahmedmakaty.base.presentation.screens.ArticleDetails;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ahmedmakaty.base.R;
import com.example.ahmedmakaty.base.data.Constants;
import com.example.ahmedmakaty.base.data.model.Article;
import com.example.ahmedmakaty.base.presentation.BaseActivity;
import com.example.ahmedmakaty.base.presentation.BaseFragment;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class ArticleDetailsFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header)
    ImageView header;
    @BindView(R.id.source)
    TextView source;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;

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

        fillArticleData();
    }

    private void fillArticleData() {
        Glide.with(getContext())
                .load(articleDetailsViewModel.getArticle().getUrlToImage())
                .into(header);

        title.setText(articleDetailsViewModel.getArticle().getTitle());
        source.setText(articleDetailsViewModel.getArticle().getSource().getName());
        desc.setText(articleDetailsViewModel.getArticle().getDescription());
        author.setText(articleDetailsViewModel.getArticle().getAuthor());

        String[] array = articleDetailsViewModel.getArticle().getContent().split("\\[");

        content.setText(array[0]);

        date.setText(formatPrettyDate(articleDetailsViewModel.getArticle().getPublishedAt(), "yyyy-MM-dd'T'HH:mm:ss", "MMM d HH:mm"));
    }

    private String formatPrettyDate(String publishedAt, String fromDormat, String toFormat) {
        String time = publishedAt;
        SimpleDateFormat spf = new SimpleDateFormat(fromDormat);
        Date newDate = null;
        try {
            newDate = spf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat(toFormat);
        time = spf.format(newDate);
        return time;
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
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle(articleDetailsViewModel.getArticle().getTitle());
        ((BaseActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((BaseActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
    }

    @OnClick(R.id.more)
    public void moreClicked(){
        //Toast.makeText(getContext(), "more", Toast.LENGTH_SHORT).show();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleDetailsViewModel.getArticle().getUrl()));
        startActivity(browserIntent);
    }
}
