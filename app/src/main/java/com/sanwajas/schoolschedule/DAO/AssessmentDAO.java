package com.sanwajas.schoolschedule.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sanwajas.schoolschedule.Entities.AssessmentEntity;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(AssessmentEntity assessment);

    @Update
    public void update(AssessmentEntity assessment);

    @Delete
    public void delete(AssessmentEntity assessment);

    @Query("DELETE FROM assessment_table")
    public void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY assessmentID ASC")
    List<AssessmentEntity> getAllAssessments();

}
