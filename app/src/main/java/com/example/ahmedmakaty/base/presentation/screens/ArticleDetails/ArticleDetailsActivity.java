package com.example.ahmedmakaty.base.presentation.screens.ArticleDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ahmedmakaty.base.data.Constants;
import com.example.ahmedmakaty.base.presentation.BaseActivity;
import com.example.ahmedmakaty.base.presentation.BaseFragment;

public class ArticleDetailsActivity extends BaseActivity {


    @Override
    public BaseFragment getFragment() {
        return ArticleDetailsFragment.newInstance(getIntent().getExtras().getString(Constants.ARTICLE_MODEL));
    }
}
