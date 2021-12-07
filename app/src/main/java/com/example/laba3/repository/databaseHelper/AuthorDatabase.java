package com.example.laba3.repository.databaseHelper;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {AuthorEntity.class}, version = 2, exportSchema = false)
public abstract class AuthorDatabase extends RoomDatabase {
    public abstract AuthorDao authorDao();
}
