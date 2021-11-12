package com.example.laba3.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private EditText authorBindingEditText;
    private EditText authorPriceEditText;
    private EditText authorNumberOfPagesEditText;
    private EditText authorYearOfPublishingEditText;
    private AutoCompleteTextView authorPublishingHouseEditText;
    private Button saveButton;

    private AddActivityPresenter presenter;
    private String[] hints = {"Альпина", "АСТ", "Наследие", "МуракамиМания", "Альтернатива"};

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
            authorPublishingHouseEditText.setText(author.getPublishingHouse());
            authorYearOfPublishingEditText.setText(String.format("%d", author.getYearOfPublishing()));
            authorBindingEditText.setText(author.getBinding());
            authorPriceEditText.setText(String.format("%f",author.getPrice()));
            authorNumberOfPagesEditText.setText(String.format("%d", author.getNumberOfPages()));
        }
    }

    private void initViews() {
        authorNameEditText = findViewById(R.id.authorNameEditText);
        authorBookEditText = findViewById(R.id.authorBookEditText);
        authorNumberOfPagesEditText = findViewById(R.id.authorNumberOfPagesEditText);
        authorPriceEditText = findViewById(R.id.authorPriceEditText);
        authorBindingEditText = findViewById(R.id.authorBindingEditText);
        authorYearOfPublishingEditText = findViewById(R.id.authorYearOfPublishingEditText);
        authorPublishingHouseEditText = findViewById(R.id.authorPublishingHouseEditText);
        saveButton = findViewById(R.id.saveButton);
        ArrayAdapter<String> autocompleteAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, hints);
        authorPublishingHouseEditText.setAdapter(autocompleteAdapter);
    }

    public void onSave(View v) {
        presenter.onSave(authorNameEditText.getText().toString(),
                authorBookEditText.getText().toString(),
                authorPublishingHouseEditText.getText().toString(),
                Integer.parseInt(authorYearOfPublishingEditText.getText().toString()),
                Integer.parseInt(authorNumberOfPagesEditText.getText().toString()),
                Double.parseDouble(authorPriceEditText.getText().toString()),
                authorBindingEditText.getText().toString());
    }
}