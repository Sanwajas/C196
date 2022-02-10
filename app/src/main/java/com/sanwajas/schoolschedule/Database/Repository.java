package com.sanwajas.schoolschedule.Database;

import android.app.Application;

import com.sanwajas.schoolschedule.DAO.AssessmentDAO;
import com.sanwajas.schoolschedule.DAO.ClassDAO;
import com.sanwajas.schoolschedule.DAO.TermDAO;
import com.sanwajas.schoolschedule.Entities.AssessmentEntity;
import com.sanwajas.schoolschedule.Entities.ClassEntity;
import com.sanwajas.schoolschedule.Entities.TermEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private List<AssessmentEntity> mAllAssesments;

    private ClassDAO mClassDAO;
    private List<ClassEntity> mAllClasses;

    private TermDAO mTermDAO;
    private List<TermEntity> mAllTerms;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mClassDAO = db.classDAO();
        mTermDAO = db.termDAO();
    }

    //Get All Commands
    public List<AssessmentEntity> getmAllAssesments(){
        databaseExecutor.execute(()-> {
            mAllAssesments = mAssessmentDAO.getAllAssessments();
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssesments;
    }

    public List<ClassEntity> getAllClasses(){
        databaseExecutor.execute(()-> {
            mAllClasses = mClassDAO.getAllClasses();
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllClasses;
    }

    public List<TermEntity> getAllTerms(){
        databaseExecutor.execute(()-> {
            mAllTerms = mTermDAO.getAllTerms();
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    // Insert Commands

    public void insert(AssessmentEntity assessment){
        databaseExecutor.execute(()-> {
            mAssessmentDAO.insert(assessment);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insert(ClassEntity classEntity){
        databaseExecutor.execute(()-> {
            mClassDAO.insert(classEntity);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insert(TermEntity term){
        databaseExecutor.execute(()-> {
            mTermDAO.insert(term);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(AssessmentEntity assessmentEntity){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessmentEntity);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(ClassEntity classEntity){
        databaseExecutor.execute(()->{
            mClassDAO.delete(classEntity);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(TermEntity termEntity){
        databaseExecutor.execute(()->{
            mTermDAO.delete(termEntity);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(AssessmentEntity assessmentEntity){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessmentEntity);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(ClassEntity classEntity){
        databaseExecutor.execute(()->{
            mClassDAO.update(classEntity);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(TermEntity termEntity){
        databaseExecutor.execute(()->{
            mTermDAO.update(termEntity);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
