package com.example.laba3.activity;



import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.laba3.fragments.BookInfoFragment;
import com.example.laba3.R;
import com.example.laba3.model.Author;
import com.example.laba3.presenter.MainActivityPresenter;
import com.example.laba3.repository.FileRepository;
import com.example.laba3.view.MainView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainActivity extends MainView {

    private FloatingActionButton addAuthorButton;
    private ListView authorsListView;
    private ArrayAdapter<String> adapter;
    private Fragment detailsFragment;
    private boolean backFlag;

    MainActivityPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Загрузка данных...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();

        presenter = new MainActivityPresenter(this, FileRepository.getInstance(getFilesDir()));
        initViews();
        presenter.init();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressDialog.setProgress(100);


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
                    .setItems(new String[]{"Удалить", "Редактировать", "Смена темы"}, (v, action) -> {
                        switch (action) {
                            case 0:
                                presenter.onDeleteAuthor(index);
                                break;
                            case 1:
                                presenter.onEditAuthor(index);
                                break;
                            case 2:
                                if(!backFlag){
                                    findViewById(R.id.authorsListView).setBackgroundColor(Color.CYAN);
                                }
                                else
                                    findViewById(R.id.authorsListView).setBackgroundColor(Color.WHITE);
                                backFlag = !backFlag;
                                break;
                        }
                    });
            builder.create().show();
            return true;
        });
        authorsListView.setOnItemClickListener((parent, item, index, id) -> {
            presenter.onDetails(index);
        });
    }

    @Override
    public void showAuthorList(List<Author> authorList) {
        final List<String> list = authorList.stream().map(Author::toString).collect(Collectors.toList());
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void showAuthorDetails(Author author) {
        if (detailsFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(detailsFragment).commit();
        }
        detailsFragment = BookInfoFragment.newInstance(author);
        findViewById(R.id.fragmentView).setVisibility(View.VISIBLE);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .add(R.id.fragmentView, detailsFragment, "DETAILS_FRAGMENT")
                .commit();
    }

    public void onAddButtonClick(View v) {
        presenter.onAddButtonClick();
    }

    public void onRefreshButtonClick(View v) {
        presenter.onRefreshButtonClick();
    }

    @Override
    public void onBackPressed() {
        if(detailsFragment == null)
            super.onBackPressed();
        else{
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .remove(detailsFragment).commit();
            findViewById(R.id.fragmentView).setVisibility(View.INVISIBLE);
            detailsFragment = null;
        }
    }

    public void onSearchButtonClick(View view) {
        presenter.onSearchClick();
    }
}