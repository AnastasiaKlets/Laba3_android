package com.example.laba3.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.laba3.R;
import com.example.laba3.model.Author;
import com.example.laba3.presenter.AddActivityPresenter;
import com.example.laba3.repository.FileRepository;
import com.example.laba3.view.AddView;

public class AddActivity extends AddView {

    private EditText authorNameEditText;
    private EditText authorBookEditText;
    private Button saveButton;

    private AddActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initViews();
        Author author = (Author) getIntent().getSerializableExtra("author");
        presenter = new AddActivityPresenter(author, FileRepository.getInstance(getFilesDir()), this);
        if(author != null) {
            authorNameEditText.setText(author.getName());
            authorBookEditText.setText(author.getBook());
        }
    }

    private void initViews() {
        authorNameEditText = findViewById(R.id.authorNameEditText);
        authorBookEditText = findViewById(R.id.authorBookEditText);
        saveButton = findViewById(R.id.saveButton);
    }

    public void onSave(View v) {
        presenter.onSave(authorNameEditText.getText().toString(), authorBookEditText.getText().toString());
    }
}