package com.example.laba3.activity;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.laba3.R;
import com.example.laba3.fragments.BookInfoFragment;
import com.example.laba3.model.Author;
import com.example.laba3.presenter.MainActivityPresenter;
import com.example.laba3.repository.DatabaseRepository;
import com.example.laba3.view.MainView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainActivity extends MainView {

    MainActivityPresenter presenter;
    private FloatingActionButton addAuthorButton;
    private ListView authorsListView;
    private ArrayAdapter<String> adapter;
    private Fragment detailsFragment;
    private boolean backFlag;

    ImageView img;
    Button start;
    Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img =findViewById(R.id.catImg);
        start = findViewById(R.id.startButton);
        stop = findViewById(R.id.stopButton);
        img.setBackgroundResource(R.drawable.cat_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable)img.getBackground();
        frameAnimation.start();
        presenter = new MainActivityPresenter(this,
                new DatabaseRepository(getApplicationContext(),
                        "data.sqlite3"));
        initViews();
        presenter.init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Загрузка данных...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.show();
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                this.runOnUiThread(() -> progressDialog.setProgress(100));
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.runOnUiThread(progressDialog::hide);
        });
        thread.start();

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
                                if (!backFlag) {
                                    findViewById(R.id.authorsListView).setBackgroundColor(Color.CYAN);
                                } else
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
        final List<String> list = authorList.stream().map(Author::toStringNam).collect(Collectors.toList());
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
        if (detailsFragment == null)
            super.onBackPressed();
        else {
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