package com.example.laba3.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.example.laba3.R;
import com.example.laba3.model.Author;
import com.example.laba3.presenter.MainActivityPresenter;
import com.example.laba3.repository.FileRepository;
import com.example.laba3.view.MainView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends MainView {

    private FloatingActionButton addAuthorButton;
    private ListView authorsListView;
    private ArrayAdapter<String> adapter;

    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this, FileRepository.getInstance(getFilesDir()));
        initViews();
        presenter.init();
    }

    private void initViews() {
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);
        addAuthorButton = findViewById(R.id.addAuthorButton);
        authorsListView = findViewById(R.id.authorsListView);
        authorsListView.setAdapter(adapter);
        authorsListView.setOnItemLongClickListener((parent, item, index, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Выберите действие")
                    .setItems(new String[]{"Удалить", "Редактировать"}, (v, action) -> {
                        if(action == 0) {
                            presenter.onDeleteAuthor(index);
                        } else {
                            presenter.onEditAuthor(index);
                        }
                    });
            builder.create().show();
            return false;
        });
    }

    @Override
    public void showAuthorList(List<Author> authorList) {
        final List<String> list = authorList.stream().map(Author::toString).collect(Collectors.toList());
        adapter.clear();
        adapter.addAll(list);
    }

    public void onAddButtonClick(View v) {
        presenter.onAddButtonClick();
    }

    public void onRefreshButtonClick(View v) {
        presenter.onRefreshButtonClick();
    }
}