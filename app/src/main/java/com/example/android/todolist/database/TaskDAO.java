package com.example.android.todolist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface TaskDAO {

    @Query("Select * from task_entry ORDER BY priority")
    LiveData<List<TaskEntry>> getAllTasks();
    @Insert
    void insert(TaskEntry entry);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(TaskEntry taskEntry);
    @Delete
    void delete(TaskEntry entry);
    @Query("Select * from task_entry where Id=:taskId")
    LiveData<TaskEntry> getTaskById(int taskId);
}
