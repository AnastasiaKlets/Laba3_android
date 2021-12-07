package com.example.laba3.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.laba3.model.Author;
import com.example.laba3.repository.databaseHelper.AuthorDatabase;
import com.example.laba3.repository.databaseHelper.AuthorEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DatabaseRepository implements AuthorRepository {
    private Context appContext;
    private String databaseName;
    private static AuthorDatabase db;

    public DatabaseRepository(Context appContext, String databaseName) {
        this.appContext = appContext;
        this.databaseName = databaseName;
        if (db == null) {
            db = Room.databaseBuilder(appContext, AuthorDatabase.class, databaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }


    @Override
    public List<Author> getAuthorList() {
        return db.authorDao()
                .getAll()
                .stream()
                .map(AuthorEntity::desugarEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void saveAuthor(Author author) {
        db.authorDao().insert(AuthorEntity.mapEntity(author));
    }

    @Override
    public void deleteAuthor(int id) {
        AuthorEntity entity =  db.authorDao().getById(id);
        if(entity != null){
            db.authorDao().delete(entity);
        }
    }

    @Override
    public List<Author> executeSearchQuery(String query, String field) {

        return null;
    }

}
