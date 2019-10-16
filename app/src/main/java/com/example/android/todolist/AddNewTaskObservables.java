package com.example.android.todolist;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.example.android.todolist.database.TaskEntry;

public class AddNewTaskObservables extends BaseObservable {

    private ObservableField<String> taskName=new ObservableField<>();

    public ObservableField<String> getTaskName() {
        return taskName;
    }

    public void setValues(TaskEntry values){
        taskName.set(values.getDescription());
    }
}
