package com.example.laba3.model;

import java.io.Serializable;

public class Author implements Serializable {
    private int id;
    private String name;
    private String book;
    private String publishingHouse;
    private int yearOfPublishing;
    private int numberOfPages;
    private double price;
    private String binding;

    public Author(int id, String name, String book, String publishingHouse, int yearOfPublishing, int numberOfPages, double price, String binding) {
        this.id = id;
        this.name = name;
        this.book = book;
        this.publishingHouse = publishingHouse;
        this.yearOfPublishing = yearOfPublishing;
        this.numberOfPages = numberOfPages;
        this.price = price;
        this.binding = binding;
    }


    public String getPublishingHouse() { return publishingHouse; }

    public void setPublishingHouse(String publishingHouse) { this.publishingHouse = publishingHouse; }

    public int getYearOfPublishing() { return yearOfPublishing; }

    public void setYearOfPublishing(int yearOfPublishing) { this.yearOfPublishing = yearOfPublishing; }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBinding() { return binding;}

    public void setBinding(String binding) { this.binding = binding;}

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
        return  "Название книги: '" + book + '\'' +
                ", Автор(ы): '" + name + '\'' +
                ", Издательство: '" + publishingHouse + '\'' +
                ", Год издания: " + yearOfPublishing +
                ", Количество страниц: " + numberOfPages +
                ", Цена: " + price +
                ", Переплет: '" + binding;
    }
    public String toStringNam() {
        return  "Название книги: '" + book + '\n' +
                "Автор(ы): '" + name;
    }
}
