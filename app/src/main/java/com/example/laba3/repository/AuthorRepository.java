package com.example.laba3.repository;

import com.example.laba3.model.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> getAuthorList();

    void saveAuthor(Author author);
    void deleteAuthor(int index);
    List<Author> executeSearchQuery(String query, String field);
}
