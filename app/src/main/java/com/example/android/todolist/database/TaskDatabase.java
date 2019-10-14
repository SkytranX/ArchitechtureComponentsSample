package com.example.android.todolist.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {TaskEntry.class},version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class TaskDatabase extends RoomDatabase {

    private static final String LOG_TAG = TaskDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "todolist";
    private static TaskDatabase sInstance;

    public static TaskDatabase getInstance(Context context){
        if(sInstance == null){
           synchronized (LOCK){
               sInstance = Room.databaseBuilder(context.getApplicationContext(),
                       TaskDatabase.class, TaskDatabase.DATABASE_NAME)
                       .build();

           }
        }
        return sInstance;
    }
     public abstract TaskDAO taskDAO();
}
