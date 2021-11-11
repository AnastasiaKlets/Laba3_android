package com.example.laba3.presenter;


import androidx.appcompat.app.AppCompatActivity;

import com.example.laba3.model.Author;
import com.example.laba3.repository.AuthorRepository;

import java.util.List;

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

    public List<Author> searchQuery(String query){
        return repository.executeSearchQuery(query, publisherQuery?"publishingHouse":"name");
    }

    public List<Author> getAllRecords(){
        return repository.getAuthorList();
    }
}
