package com.example.laba3.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.laba3.activity.AddActivity;
import com.example.laba3.model.Author;
import com.example.laba3.repository.AuthorRepository;
import com.example.laba3.view.MainView;

import java.util.List;

public class MainActivityPresenter {
    private final MainView view;
    private final AuthorRepository repository;

    public MainActivityPresenter(MainView view, AuthorRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void init() {
        List<Author> authorList = repository.getAuthorList();
        view.showAuthorList(authorList);
    }

    public void onAddButtonClick() {
        view.startActivity(new Intent(view, AddActivity.class));
    }

    public void onRefreshButtonClick() {
        List<Author> authorList = repository.getAuthorList();
        view.showAuthorList(authorList);
    }

    public void onEditAuthor(int index) {
        Author author = repository.getAuthorList().get(index);
        Intent intent = new Intent(view, AddActivity.class);
        intent.putExtra("author", author);
        view.startActivity(intent);
    }

    public void onDeleteAuthor(int index) {
        repository.deleteAuthor(index);
        onRefreshButtonClick();
    }

    public void onDetails(int index){
        Author author =  repository.getAuthorList().get(index);
        view.showAuthorDetails(author);

    }


}
