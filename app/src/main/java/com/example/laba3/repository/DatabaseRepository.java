package com.example.laba3.repository;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.room.Room;

import com.example.laba3.model.Author;
import com.example.laba3.repository.databaseHelper.AuthorDatabase;
import com.example.laba3.repository.databaseHelper.AuthorEntity;
import com.example.laba3.services.DatabaseHelperService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseRepository implements AuthorRepository {
    private final Context appContext;
    private final String databaseName;
    private static AuthorDatabase db;
    private final InternalServiceConnection serviceConnection = new InternalServiceConnection();
    private DatabaseHelperService service;
    private ArrayList<DataPreparedInRepository> dataPreparedListeners = new ArrayList<>();


    public DatabaseRepository(Context appContext, String databaseName) {
        this.appContext = appContext;
        this.databaseName = databaseName;
//        if (db == null) {
//            db = Room.databaseBuilder(appContext, AuthorDatabase.class, databaseName)
//                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
//                    .build();
//        }
        Intent intent = new Intent(appContext, DatabaseHelperService.class);
        appContext.startService(intent);
        appContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    public List<Author> getAuthorList() {
        if(service != null)
            return service.getAuthorList();
        else
            return new ArrayList<>();
    }

    @Override
    public void saveAuthor(Author author) {
        if(service != null)
            service.saveAuthor(author);
    }

    @Override
    public void deleteAuthor(int id) {
        if(service != null)
            service.deleteAuthor(id);
    }

    @Override
    public List<Author> executeSearchQuery(String query, String field) {
        if (service != null){
            return service.executeSearchQuery(query, field);
        }
        return new ArrayList<Author>();
    }

    @Override
    public void setOnDataPreparedListener(DataPreparedInRepository listener) {
        dataPreparedListeners.add(listener);
    }

    private void notifyDataListeners(){
        for (DataPreparedInRepository listener: dataPreparedListeners) {
            try{
            if (listener != null){
                listener.onDataPrepared();
            }}
            catch (Exception ex){
                Log.d("notifyDataListeners", ex.getMessage());
            }
        }
    }

    private class InternalServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DatabaseHelperService service_ = DatabaseRepository.this.service =
                    ((DatabaseHelperService.DatabaseServiceBinder) service)
                            .getService();
            Log.d("DOSC", "onServiceConnected");
            service_.setDatabaseName(DatabaseRepository.this.databaseName);
            service_.setAppContext(DatabaseRepository.this.appContext);
            service_.initDatabase();
            DatabaseRepository.this.notifyDataListeners();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("DOSD", "onServiceDisconnected");
        }
    }

}
