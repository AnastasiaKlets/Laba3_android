package com.example.laba3.repository;

import com.example.laba3.model.Author;

import java.util.ArrayList;
import java.util.List;

public class MockRepository implements AuthorRepository {
    private final List<Author> authorList = new ArrayList<Author>();

    private static final MockRepository repository = new MockRepository();
    public static AuthorRepository getInstance(){
        return repository;
    }

    public MockRepository() {
        authorList.add(new Author(1, "asdf", "asdfeqwr"));
        authorList.add(new Author(2, "wqef", "asdfeqwr"));
        authorList.add(new Author(3, "zxcv", "asdfeqwr"));
    }

    @Override
    public List<Author> getAuthorList() {
        return authorList;
    }

    @Override
    public void saveAuthor(Author author) {
        if(author.getId() == 0) {
            authorList.add(author);
        } else {
            authorList.removeIf(x -> x.getId() == author.getId());
            authorList.add(author);
        }
    }

    @Override
    public void deleteAuthor(int index) {
        authorList.remove(index);
    }
}
