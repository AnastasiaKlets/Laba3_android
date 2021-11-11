package com.example.laba3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.laba3.R;
import com.example.laba3.view.SearchView;


public class SearchActivity extends SearchView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}