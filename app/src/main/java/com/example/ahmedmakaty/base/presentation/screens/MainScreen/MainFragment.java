package com.example.ahmedmakaty.base.presentation.screens.MainScreen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.example.ahmedmakaty.base.R;
import com.example.ahmedmakaty.base.presentation.BaseFragment;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;

public class MainFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.empty_list_note)
    TextView emptyState;

    private AlertDialog mProgress;

    @Inject
    MainViewModelFactory mainViewModelFactory;

    MainScreenAdapter mainScreenAdapter;

    MainViewModel mainViewModel;

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

        //initializeToolbar();

        initClickListeners();

        setupRecyclerView();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel.class);

        initiateLiveObservers();

    }

    private void initiateLiveObservers() {

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(mainScreenAdapter);
    }

    private void showErrorMessage(String s) {
        Snackbar.make(getView(), s, Snackbar.LENGTH_LONG);
    }

    public void initializeToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.main_title));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void initClickListeners() {

//        addNewCard.setOnClickListener((View v) -> {
//            goToPayfortScreen();
//        });

    }

    private void showProgress(Boolean show) {
        if (mProgress != null) {
            if (show) {
                mProgress.show();
            } else {
                mProgress.dismiss();
            }
        }
    }

    private void onBackPressed() {
        getActivity().finish();
    }

}
