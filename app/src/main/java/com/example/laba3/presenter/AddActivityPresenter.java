package com.example.laba3.presenter;

import com.example.laba3.model.Author;
import com.example.laba3.repository.AuthorRepository;
import com.example.laba3.view.AddView;

public class AddActivityPresenter {
    final AuthorRepository repository;
    final AddView view;
    final Author author;

    public AddActivityPresenter(Author author, AuthorRepository repository, AddView view) {
        this.repository = repository;
        this.view = view;
        if(author != null) {
            this.author = author;
        } else {
            this.author = new Author(0, "", "");
        }
    }

    public void onSave(String name, String book) {
        author.setName(name);
        author.setBook(book);
        repository.saveAuthor(author);
        view.onBackPressed();
    }
}
