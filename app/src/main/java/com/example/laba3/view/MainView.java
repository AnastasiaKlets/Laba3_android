package com.example.laba3.view;

import android.app.Activity;

import com.example.laba3.model.Author;

import java.util.List;

public abstract class MainView extends Activity {
    public abstract void showAuthorList(List<Author> authorList);
}
