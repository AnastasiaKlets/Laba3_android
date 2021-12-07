package com.example.laba3.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.laba3.R;
import com.example.laba3.model.Author;
import com.example.laba3.presenter.SearchActivityPresenter;
import com.example.laba3.repository.DatabaseRepository;
import com.example.laba3.repository.FileRepository;
import com.example.laba3.view.SearchView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class SearchActivity extends SearchView {
    private ListView lv1;
    private ArrayAdapter<String> adapter;
    private SearchActivityPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        presenter = new SearchActivityPresenter(this, new DatabaseRepository(getApplicationContext(), "data.sqlite3"));

        ((SwitchCompat)findViewById(R.id.switch2)).setOnCheckedChangeListener((buttonView, isChecked) -> presenter.toggleQuerySwitch());
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ((android.widget.SearchView)findViewById(R.id.searchView)).setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Author> res =  presenter.searchQuery(newText);
                adapter.clear();
                adapter.addAll(res.stream().map(Author::toString).collect(Collectors.toList()));
                return false;
            }
        });
        List<String> a = presenter.getAllRecords().stream().map(Author::toString).collect(Collectors.toList());
        adapter.addAll(presenter.getAllRecords().stream().map(Author::toString).collect(Collectors.toList()));
        lv1 = findViewById(R.id.lv1);
        lv1.setAdapter(adapter);
    }


}