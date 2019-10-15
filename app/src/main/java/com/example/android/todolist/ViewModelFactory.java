package com.example.android.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.todolist.database.TaskDatabase;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    TaskDatabase mDB;
    int taskId;

    public ViewModelFactory(TaskDatabase mDB, int taskId) {
        this.mDB = mDB;
        this.taskId = taskId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(taskId,mDB);
    }
}
