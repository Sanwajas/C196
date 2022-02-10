package com.sanwajas.schoolschedule.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sanwajas.schoolschedule.Entities.ClassEntity;

import java.util.List;

@Dao
public interface ClassDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ClassEntity classes);

    @Update
    void update(ClassEntity classes);

    @Delete
    void delete(ClassEntity classes);

    @Query("DELETE FROM class_table")
    void deleteAllClasses();

    @Query("SELECT * FROM class_table ORDER BY classID ASC")
    List<ClassEntity> getAllClasses();
}
