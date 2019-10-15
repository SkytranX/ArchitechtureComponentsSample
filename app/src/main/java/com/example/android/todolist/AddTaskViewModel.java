package com.example.android.todolist;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.todolist.database.TaskDatabase;
import com.example.android.todolist.database.TaskEntry;

import static android.content.ContentValues.TAG;

public class AddTaskViewModel extends ViewModel {
    private static final String TAG = AddTaskViewModel.class.getSimpleName();
    private LiveData<TaskEntry> taskEntryLiveData;

    public AddTaskViewModel(@NonNull int taskId, @NonNull TaskDatabase mDB) {
        Log.e(TAG, "Data fetched in add task " );
        this.taskEntryLiveData = mDB.taskDAO().getTaskById(taskId);
    }

    public LiveData<TaskEntry> getTaskEntryLiveData() {
        return taskEntryLiveData;
    }
}
