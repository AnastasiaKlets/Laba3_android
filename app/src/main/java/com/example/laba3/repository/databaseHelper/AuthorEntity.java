package com.example.laba3.repository.databaseHelper;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.laba3.model.Author;
import com.example.laba3.repository.AuthorRepository;
import com.example.laba3.repository.DatabaseRepository;

@Entity(tableName = "author")
public class AuthorEntity extends Author {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String book;

    public String publishingHouse;
    public int yearOfPublishing;
    public int numberOfPages;
    public double price;
    public String binding;

    public AuthorEntity(int id, String name, String book, String publishingHouse, int yearOfPublishing, int numberOfPages, double price, String binding) {
        super(id, name, book, publishingHouse, yearOfPublishing, numberOfPages, price, binding);
        this.id = id;
        this.name = name;
        this.publishingHouse = publishingHouse;
        this.yearOfPublishing = yearOfPublishing;
        this.numberOfPages = numberOfPages;
        this.price = price;
        this.binding = binding;
        this.book = book;
    }

    public static Author desugarEntity(AuthorEntity entity) {
        if (entity != null)
            return new Author(entity.id,
                    entity.name,
                    entity.book,
                    entity.publishingHouse,
                    entity.yearOfPublishing,
                    entity.numberOfPages,
                    entity.price,
                    entity.binding);
        else
            return null;
    }

    public static AuthorEntity mapEntity(Author entity){
        if (entity != null)
            return new AuthorEntity(entity.getId(),
                    entity.getName(),
                    entity.getBook(),
                    entity.getPublishingHouse(),
                    entity.getYearOfPublishing(),
                    entity.getNumberOfPages(),
                    entity.getPrice(),
                    entity.getBinding());
        else
            return null;
    }
}
