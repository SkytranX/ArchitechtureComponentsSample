package com.example.android.todolist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.android.todolist.database.TaskDatabase;
import com.example.android.todolist.database.TaskEntry;

import java.util.Date;

import static android.content.ContentValues.TAG;

public class AddTaskViewModel extends AndroidViewModel {

    private AddNewTaskObservables newTaskObservables=new AddNewTaskObservables();
    private LiveData<TaskEntry> task;
    private final TaskDatabase appDatabase;

    private final MutableLiveData<Boolean> dataSaving=new MutableLiveData<>();
//    private static final String TAG = AddTaskViewModel.class.getSimpleName();

    public AddTaskViewModel(@NonNull Application application){
        super(application);
        appDatabase=TaskDatabase.getInstance(application);
    }

    public AddNewTaskObservables getNewTaskObservables() {
        return newTaskObservables;
    }

    public void getTaskByTaskId(LifecycleOwner owner, int taskId){
        task=appDatabase.taskDAO().getTaskById(taskId);
       // Anonymous Observer class exist without a name
        task.observe(owner, new Observer<TaskEntry>() {
            @Override
            public void onChanged(TaskEntry entry) {
                task.removeObserver(this);
                newTaskObservables.setValues(entry);
            }
        });
//        if (task!=null && task.getValue()!=null)
//            newTaskObservables.setValues(task.getValue());
    }

    public MutableLiveData<Boolean> getDataSaving() {
        return dataSaving;
    }

    public void saveData(int priority){
        dataSaving.setValue(false);
        String description = newTaskObservables.getTaskName().get();
        Date date = new Date();

        final TaskEntry newTask = new TaskEntry(description, priority, date);
        //.execute((new Runnable) ) > alt+enter change to Lambda
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            if(task==null || task.getValue()==null){
                appDatabase.taskDAO().insert(newTask);
            } else {
                newTask.setId(task.getValue().getId());
                appDatabase.taskDAO().update(newTask);
            }
            dataSaving.postValue(false);
        });
    }
        public LiveData<TaskEntry> getTask() {
        return task;
    }
    //    public AddTaskViewModel( TaskDatabase mDB, ) {
//        Log.e(TAG, "Data fetched in add task " );
//        this.taskEntryLiveData = mDB.taskDAO().getTaskById(taskId);
//        task = taskId
//    }
}
