package com.sanwajas.schoolschedule.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sanwajas.schoolschedule.Database.Repository;
import com.sanwajas.schoolschedule.Entities.AssessmentEntity;
import com.sanwajas.schoolschedule.Entities.ClassEntity;
import com.sanwajas.schoolschedule.Entities.TermEntity;
import com.sanwajas.schoolschedule.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository repository = new Repository(getApplication());
        TermEntity termEntity = new TermEntity(1,"Term 1", "01/01/2020", "06/30/2020");
        repository.insert(termEntity);
        TermEntity termEntity2 = new TermEntity(2,"Term 2", "01/01/2020", "06/30/2020");
        repository.insert(termEntity2);
        TermEntity termEntity3 = new TermEntity(3,"Term 3", "01/01/2020", "06/30/2020");
        repository.insert(termEntity3);
        ClassEntity classEntity = new ClassEntity(1,"Class 1","01/01/2022","06/06/2022","In-Progress","Dr. Huggins","555-555-1212","DrHuggins@school.com","",1);
        repository.insert(classEntity);
        ClassEntity classEntity2 = new ClassEntity(2,"Class 2","01/01/2022","06/06/2022","In-Progress","Dr. Huggins","555-555-1212","DrHuggins@school.com","",1);
        repository.insert(classEntity2);
        ClassEntity classEntity3 = new ClassEntity(3,"Class 3","01/01/2022","06/06/2022","In-Progress","Dr. Huggins","555-555-1212","DrHuggins@school.com","",1);
        repository.insert(classEntity3);
        AssessmentEntity assessmentEntity = new AssessmentEntity(1,"Test 1","Objective","05/20/2022",1);
        repository.insert(assessmentEntity);
        AssessmentEntity assessmentEntity2 = new AssessmentEntity(2,"Test 2","Oral","05/20/2022",1);
        repository.insert(assessmentEntity2);
        AssessmentEntity assessmentEntity3 = new AssessmentEntity(3,"Test 3","Oral","05/20/2022",1);
        repository.insert(assessmentEntity3);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(MainActivity.this, OptionsActivity.class));
            }
        },1500 );

    }


}