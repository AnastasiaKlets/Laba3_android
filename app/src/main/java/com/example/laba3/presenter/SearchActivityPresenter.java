package com.example.laba3.presenter;


import androidx.appcompat.app.AppCompatActivity;

import com.example.laba3.repository.AuthorRepository;

public class SearchActivityPresenter {
    private final AppCompatActivity activity;
    private final AuthorRepository repository;
    private boolean publisherQuery = false;

    public SearchActivityPresenter(AppCompatActivity activity, AuthorRepository repository){
        this.activity = activity;
        this.repository = repository;
    }

    public void toggleQuerySwitch(){
        publisherQuery = !publisherQuery;
    }
}
