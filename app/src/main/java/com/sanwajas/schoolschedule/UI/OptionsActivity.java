package com.sanwajas.schoolschedule.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sanwajas.schoolschedule.Database.Repository;
import com.sanwajas.schoolschedule.Entities.AssessmentEntity;
import com.sanwajas.schoolschedule.Entities.ClassEntity;
import com.sanwajas.schoolschedule.Entities.TermEntity;
import com.sanwajas.schoolschedule.R;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_main);

    }

    public void loadAllTerms(View view){
        startActivity(new Intent(OptionsActivity.this, TermsActivity.class));
    }

    public void loadAllClasses(View view){
        startActivity(new Intent(OptionsActivity.this, ClassActivity.class));
    }

    public void loadAllAssessments(View view){
        startActivity(new Intent(OptionsActivity.this, AssessmentActivity.class));
    }



}