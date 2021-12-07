package com.example.laba3.repository.databaseHelper;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AuthorDao {
    @Query("SELECT * FROM author")
    List<AuthorEntity> getAll();

    @Delete
    void delete(AuthorEntity author);

    @Update
    void update(AuthorEntity author);

    @Insert
    void insert(AuthorEntity authorEntity);

    @Insert
    void insertRange(List<AuthorEntity> authors);

    @Query("SELECT * FROM author WHERE author.id = :id")
    AuthorEntity getById(int id);

    @Query("SELECT * FROM author WHERE author.name LIKE :argument")
    List<AuthorEntity> searchInBookName(String argument);

    @Query("SELECT * FROM author WHERE author.publishingHouse LIKE :argument")
    List<AuthorEntity> searchInPublisherName(String argument);
}
