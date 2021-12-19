package com.example.laba3.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.room.Room;

import com.example.laba3.model.Author;
import com.example.laba3.repository.databaseHelper.AuthorDatabase;
import com.example.laba3.repository.databaseHelper.AuthorEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class DatabaseHelperService extends Service {
    private static AuthorDatabase db;
    private final DatabaseServiceBinder binder = new DatabaseServiceBinder();
    private Context appContext;
    private String databaseName;

    public DatabaseHelperService() {
    }

    public DatabaseHelperService(Context appContext, String databaseName) {
        this.appContext = appContext;
        this.databaseName = databaseName;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public void initDatabase() {
        Log.d("SERVICE_INIT_DB", "DatabaseHelperService.initDatabase();");
        AsyncTask<Void, Void, Void> thread = new AsyncTask<Void, Void, Void>() {
            @SuppressLint({"UnsafeOptInUsageError", "StaticFieldLeak"})
            @Override
            protected Void doInBackground(Void... voids) {
                if (db == null) {
                    db = Room.databaseBuilder(appContext, AuthorDatabase.class, databaseName)
                            .fallbackToDestructiveMigration()
                            .build();
                }
                Log.d("SERVICE_INIT_DB", "init end");
                return null;
            }
        };
        try {
            thread.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Author> getAuthorList() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, List<Author>> task = new AsyncTask<Void, Void, List<Author>>() {
            @Override
            protected List<Author> doInBackground(Void... voids) {
                return db.authorDao()
                        .getAll()
                        .stream()
                        .map(AuthorEntity::desugarEntity)
                        .collect(Collectors.toList());
            }
        };
        try {
            return task.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<Author>();
    }

    public void saveAuthor(Author author) {
        AsyncTask<Author, Void, Void> task = new AsyncTask<Author, Void, Void>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Author... authors) {
                db.authorDao().insert(AuthorEntity.mapEntity(authors[0]));
                return null;
            }
        };
        task.execute();
    }

    public void deleteAuthor(int id) {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                AuthorEntity entity = db.authorDao().getById(integers[0]);
                if (entity != null) {
                    db.authorDao().delete(entity);
                }
                return null;
            }
        };
        task.execute();

    }

    public List<Author> executeSearchQuery(String query, String field) {
        List<Author> result;
        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, Void, List<Author>> task = new AsyncTask<String, Void, List<Author>>() {
            @Override
            protected List<Author> doInBackground(String... params) {
                switch (params[1]) {
                    case "name":
                        return db.authorDao()
                                .searchInBookName(params[0])
                                .stream()
                                .map(AuthorEntity::desugarEntity)
                                .collect(Collectors.toList());
                    case "publishingHouse":
                        return db.authorDao()
                                .searchInPublisherName(params[0])
                                .stream()
                                .map(AuthorEntity::desugarEntity)
                                .collect(Collectors.toList());
                    default:
                        return new ArrayList<Author>();
                }
            }
        };
        try {
            result = task.execute(query, field).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            result = new ArrayList<>();
        }
        return result;
    }


    public class DatabaseServiceBinder extends Binder {
        public DatabaseHelperService getService() {
            return DatabaseHelperService.this;
        }
    }
}
