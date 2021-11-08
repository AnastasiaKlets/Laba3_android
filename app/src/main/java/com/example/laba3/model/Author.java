package com.example.laba3.model;

import java.io.Serializable;

public class Author implements Serializable {
    private int id;
    private String name;
    private String book;

    public Author(int id, String name, String book) {
        this.id = id;
        this.name = name;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", book='" + book + '\'' +
                '}';
    }
}
