package com.example.task.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.task.ui.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllLive();

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Query("UPDATE task Set title = :newTitle, `description` = :newDesc WHERE id IN (:idList)")
    void updateSalaryByIdList(int idList, String newTitle, String newDesc);

    @Query("SELECT * FROM task ORDER BY title ASC")
    List<Task> sort();
}
