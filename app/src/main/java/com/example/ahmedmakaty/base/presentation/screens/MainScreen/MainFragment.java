package com.example.ahmedmakaty.base.presentation.screens.MainScreen;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.ahmedmakaty.base.R;
import com.example.ahmedmakaty.base.data.Constants;
import com.example.ahmedmakaty.base.data.model.Article;
import com.example.ahmedmakaty.base.presentation.BaseActivity;
import com.example.ahmedmakaty.base.presentation.BaseFragment;
import com.example.ahmedmakaty.base.presentation.helper.PaginationScrollListener;
import com.example.ahmedmakaty.base.presentation.screens.ArticleDetails.ArticleDetailsActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class MainFragment extends BaseFragment implements MainScreenAdapter.ArticleClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    ShimmerRecyclerView list;
    @BindView(R.id.empty_list_note)
    TextView emptyState;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    MainViewModelFactory mainViewModelFactory;

    MainScreenAdapter mainScreenAdapter;

    MainViewModel mainViewModel;

    ProgressDialog progressDialog;

    public static MainFragment newInstance() {

        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));

        initializeToolbar();

        setupRecyclerView();

        //Swipe to refresh functionality
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                mainViewModel.resetData();
                mainViewModel.getFeed();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel.class);

        initiateLiveObservers();

        mainViewModel.getFeed();
    }

    //Initiating live data observers
    private void initiateLiveObservers() {
        mainViewModel.loadMoreSLD.observe(this, this::onLoadMore);
        mainViewModel.progressSLD.observe(this, this::showProgress);
        mainViewModel.noInternetSLD.observe(this, this::showNoInternet);
        mainViewModel.articlesSLD.observe(this, this::addArticles);
        mainViewModel.apiErrorSLD.observe(this, this::showErrorMessage);
    }

    private void showNoInternet(Boolean aBoolean) {
        emptyState.setVisibility(View.VISIBLE);
    }

    private void addArticles(ArrayList<Article> articles) {
        list.hideShimmerAdapter();
        emptyState.setVisibility(View.GONE);
        mainScreenAdapter.setData(articles);
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mainScreenAdapter = new MainScreenAdapter(this);
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(mainScreenAdapter);
        list.showShimmerAdapter();

        //Adding a custom scroll listener to detect reaching the end of the list
        list.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                mainViewModel.loadNext();
            }

            @Override
            public boolean isLastPage() {
                return mainViewModel.fullyLoaded;
            }

            @Override
            public boolean isLoading() {
                return mainViewModel.loading;
            }
        });
    }

    //On loading state changed, adding a loading item and calling the next page
    // then removing the loading item after getting the results
    private void onLoadMore(Boolean loading) {
        if (loading) {
            //Add a dummy state to the list to show loading
            //increment page count
            mainViewModel.addDummyLoadingObjectToList();
            mainViewModel.page++;
            mainViewModel.getFeed();
        } else {
            //remove loading item
            mainViewModel.removeDummyObjectFromList();
        }
    }

    private void showErrorMessage(String s) {
        Snackbar.make(getView(), s, Snackbar.LENGTH_LONG);
    }

    public void initializeToolbar() {
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ((BaseActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitle(getResources().getString(R.string.main_title));
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.main_title));
    }

    private void showProgress(Boolean show) {
        if (progressDialog != null) {
            if (show) {
                //progressDialog.show();
                list.showShimmerAdapter();
            } else {
                //progressDialog.dismiss();
                list.hideShimmerAdapter();
            }
        }
    }

    private void onBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onArticleClicked(Article article) {
        goToArticleDetailsScreen(article);
    }

    //navigating to article details screen on recycler item click
    private void goToArticleDetailsScreen(Article article) {
        Intent intent = new Intent(getContext(), ArticleDetailsActivity.class);
        intent.putExtra(Constants.ARTICLE_MODEL, new Gson().toJson(article));
        startActivity(intent);
    }
}
