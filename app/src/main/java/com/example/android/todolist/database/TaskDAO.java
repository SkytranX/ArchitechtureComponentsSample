package com.example.android.todolist.database;

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
    List<TaskEntry> getAllTasks();
    @Insert
    void insert(TaskEntry entry);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(TaskEntry taskEntry);
    @Delete
    void delete(TaskEntry entry);
}
