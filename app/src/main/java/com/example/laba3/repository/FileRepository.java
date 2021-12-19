package com.example.laba3.repository;

import com.example.laba3.model.Author;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileRepository implements AuthorRepository{

    private static final String FILE_NAME = "authors.bin";

    private File filesDir;
    private static final FileRepository repository = new FileRepository();
    public static AuthorRepository getInstance(File filesDir){
        repository.filesDir = filesDir;
        return repository;
    }

    private void saveList(List<Author> authorList) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filesDir, FILE_NAME)));
            oos.writeObject(authorList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Author> getAuthorList() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filesDir, FILE_NAME)));
            return (List<Author>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private int findId(List<Author> authorList) {
        for (int i = 1; i < authorList.size()+1; i++) {
            int finalI = i;
            if(authorList.stream().noneMatch(x -> x.getId() == finalI)) {
                return i;
            }
        }
        return authorList.size()+2;
    }

    @Override
    public void saveAuthor(Author author) {
        List<Author> authorList = getAuthorList();
        if(author.getId() == 0) {
            author.setId(findId(authorList));
            authorList.add(author);
        } else {
            authorList.removeIf(x -> x.getId() ==  author.getId());
            authorList.add(author);
        }
        saveList(authorList);
    }

    @Override
    public void deleteAuthor(int index) {
        List<Author> authorList = getAuthorList();
        authorList.remove(index);
        saveList(authorList);
    }

    @Override
    public List<Author> executeSearchQuery(String query, String field) {
        return getAuthorList().stream().filter(e->{
            if(Objects.equals(field, "publishingHouse")){
                return e.getPublishingHouse().contains(query);
            }
            if(Objects.equals(field, "name")){
                return e.getName().contains(query);
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public void setOnDataPreparedListener(DataPreparedInRepository listener) {

    }
}
