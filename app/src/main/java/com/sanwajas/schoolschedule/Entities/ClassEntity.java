package com.sanwajas.schoolschedule.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="class_table")
public class ClassEntity {
    @PrimaryKey(autoGenerate = true)
    private int classID;
    private String className;
    private String startDate;
    private String endDate;
    private String classStatus;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private String classNotes;
    private int termID;

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getClassNotes() {
        return classNotes;
    }

    public void setClassNotes(String classNotes) {
        this.classNotes = classNotes;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public ClassEntity(int classID, String className, String startDate, String endDate, String classStatus, String instructorName, String instructorPhone, String instructorEmail, String classNotes, int termID) {
        this.classID = classID;
        this.className = className;
        this.startDate = startDate;
        this.endDate = endDate;
        this.classStatus = classStatus;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.classNotes = classNotes;
        this.termID = termID;
    }
}
