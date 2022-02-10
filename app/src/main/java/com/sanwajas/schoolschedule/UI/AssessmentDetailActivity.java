package com.sanwajas.schoolschedule.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sanwajas.schoolschedule.Database.Repository;
import com.sanwajas.schoolschedule.Entities.AssessmentEntity;
import com.sanwajas.schoolschedule.Entities.ClassEntity;
import com.sanwajas.schoolschedule.Entities.TermEntity;
import com.sanwajas.schoolschedule.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetailActivity extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    private Button dateButton;
    private TextView name;
    private Spinner course;
    private Spinner type;
    String id = null;
    ArrayAdapter<String> adapter;
    Repository repository = new Repository(getApplication());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        initDatePicker();
        dateButton = findViewById(R.id.DatePickerbutton);
        dateButton.setText(getTodaysDate());
        Spinner course = findViewById(R.id.CourseList);
        List<String> spinnerArray = getAllCourseNames();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(adapter);
        if(getIntent().getStringExtra("id") != null){
            this.id = getIntent().getStringExtra("id");
            fillFields(id);
        }
        else if(getIntent().getStringExtra("classID") != null){
            try{
                int assessmentCourseID = Integer.parseInt(getIntent().getStringExtra("classID"));
                String assessmentCourse = null;
                int i = 0;
                List<ClassEntity> mAllClasses = repository.getAllClasses();
                while(i<mAllClasses.size()){
                    if(mAllClasses.get(i).getClassID() == assessmentCourseID){
                        assessmentCourse = mAllClasses.get(i).getClassName();
                        i = mAllClasses.size();
                        course.setSelection(adapter.getPosition(assessmentCourse));
                    }
                    i=i+1;
                }

            }catch(Exception ignore){

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notification_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String assessmentName = name.getText().toString();
        String dateFromButton = dateButton.getText().toString();
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Date myDate = null;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR)-1900;
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH)-1;
        Date yesterday = new Date(year,month,day,23,59);
        try{
            myDate =  simpleDateFormat.parse(dateFromButton);
        }catch(Exception e){
            e.printStackTrace();
        }
        if(myDate.after(yesterday)) {
            Long trigger = myDate.getTime();
            Intent intent = new Intent(AssessmentDetailActivity.this, TheReceiver.class);
            intent.putExtra("key", "Assessment " + assessmentName + " is due " + dateFromButton + ".");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailActivity.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        }
        return true;
    }

    private void fillFields(String id) {
        name = findViewById(R.id.textAssessmentNameField);
        course = findViewById(R.id.CourseList);
        type = findViewById(R.id.assessmentType);
        String assessmentName = String.valueOf(getIntent().getStringExtra("name"));
        String assessmentType = String.valueOf(getIntent().getStringExtra("type"));
        int assessmentCourseID = Integer.parseInt(getIntent().getStringExtra("course"));
        String assessmentEnd = String.valueOf((getIntent().getStringExtra("end")));

        String assessmentCourse = null;
        int i = 0;
        List<ClassEntity> mAllClasses = repository.getAllClasses();
        while(i<mAllClasses.size()){
            if(mAllClasses.get(i).getClassID() == assessmentCourseID){
                assessmentCourse = mAllClasses.get(i).getClassName();
                i = mAllClasses.size();
            }
            i=i+1;
        }

        name.setText(assessmentName);
        course.setSelection(adapter.getPosition(assessmentCourse));
        String[] stringArray = getResources().getStringArray(R.array.Assessment_Types);
        type.setSelection(Arrays.asList(stringArray).indexOf(assessmentType));
        dateButton.setText(assessmentEnd);
    }

    private ArrayList<String> getAllCourseNames() {
        ArrayList<String> courseNames = new ArrayList<String>();
        List<ClassEntity> allClasses = repository.getAllClasses();
        int i = 0;
        while(i < allClasses.size()){
            courseNames.add(allClasses.get(i).getClassName());
            i=i+1;
        }
        return courseNames;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return month+"/"+day+"/"+year;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog =  new DatePickerDialog(this,style, dateSetListener,year,month,day);

    }

    private String makeDateString(int day, int month, int year){
        return month + "/"+day+"/"+year;
    }


    public void datePicker(View view) {
        datePickerDialog.show();
    }

    public void saveAssessment(View view) {
        if(id != null){
            id = getIntent().getStringExtra("id");
            int cID = Integer.parseInt(id);
            name = findViewById(R.id.textAssessmentNameField);
            course = findViewById(R.id.CourseList);
            type = findViewById(R.id.assessmentType);
            dateButton = findViewById(R.id.DatePickerbutton);

            String cName = String.valueOf(name.getText());
            String cEnd = String.valueOf(dateButton.getText());
            String cType = type.getSelectedItem().toString();
            String CCourseString = course.getSelectedItem().toString();
            List<ClassEntity> allClasses = repository.getAllClasses();
            int cCourseID = 0;
            int i = 0;
            while(i < allClasses.size()){
                if(allClasses.get(i).getClassName().equals(CCourseString)){
                    cCourseID = allClasses.get(i).getClassID();
                    i = allClasses.size();
                }
                i=i+1;
            }
            AssessmentEntity currentAssessment = new AssessmentEntity(cID, cName, cType, cEnd, cCourseID);
            repository.update(currentAssessment);
            finish();

        }
        else{
            List<AssessmentEntity> mAllAssessments = repository.getmAllAssesments();
            int last = mAllAssessments.size();
            int cID = 1;
            if(last > 0) {
                int lastID = mAllAssessments.get(last - 1).getAssessmentID();
                cID = lastID + 1;
            }
            name = findViewById(R.id.textAssessmentNameField);
            course = findViewById(R.id.CourseList);
            type = findViewById(R.id.assessmentType);
            dateButton = findViewById(R.id.DatePickerbutton);

            String cName = String.valueOf(name.getText());
            String cEnd = String.valueOf(dateButton.getText());
            String cType = type.getSelectedItem().toString();
            String CCourseString = course.getSelectedItem().toString();
            List<ClassEntity> allClasses = repository.getAllClasses();
            int cCourseID = 0;
            int i = 0;
            while(i < allClasses.size()){
                if(allClasses.get(i).getClassName().equals(CCourseString)){
                    cCourseID = allClasses.get(i).getClassID();
                    i = allClasses.size();
                }
                i=i+1;
            }
            AssessmentEntity currentAssessment = new AssessmentEntity(cID, cName, cType, cEnd, cCourseID);
            repository.insert(currentAssessment);
            finish();
        }

    }

    public void cancelAssessment(View view) {
        finish();
    }

    public void deleteAssessment(View view) {
        if(id != null){
            List<AssessmentEntity> mAllAssessments = repository.getmAllAssesments();
            AssessmentEntity current = null;
            int i=0;
            while(i < mAllAssessments.size()){
                if(id.equalsIgnoreCase(String.valueOf(mAllAssessments.get(i).getAssessmentID()))){
                    current = mAllAssessments.get(i);
                    i=mAllAssessments.size();
                }
                i = i+1;
            }
            repository.delete(current);
            Toast.makeText(AssessmentDetailActivity.this, "Deletion Complete", Toast.LENGTH_LONG).show();
            finish();
        }
        finish();

    }
}