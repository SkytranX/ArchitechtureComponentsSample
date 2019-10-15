package com.example.android.todolist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.todolist.database.TaskDatabase;
import com.example.android.todolist.database.TaskEntry;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<TaskEntry>> liveDataTasks;
    private static final String TAG = MainViewModel.class.getSimpleName();
    public MainViewModel(@NonNull Application application) {
        super(application);
        Log.e(TAG, "Actively retrieving the tasks from the DataBAse" );
        liveDataTasks=TaskDatabase.getInstance(this.getApplication()).taskDAO().getAllTasks();

    }

    public LiveData<List<TaskEntry>> getLiveDataTasks() {
        return liveDataTasks;
    }
}
