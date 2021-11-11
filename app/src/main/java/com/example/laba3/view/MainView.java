package com.example.laba3.view;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laba3.model.Author;

import java.util.List;

public abstract class MainView extends AppCompatActivity {
    public abstract void showAuthorList(List<Author> authorList);
    public abstract void showAuthorDetails(Author author);
}
