package com.sanwajas.schoolschedule.UI;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanwajas.schoolschedule.Database.Repository;
import com.sanwajas.schoolschedule.Entities.AssessmentEntity;
import com.sanwajas.schoolschedule.Entities.ClassEntity;
import com.sanwajas.schoolschedule.Entities.TermEntity;
import com.sanwajas.schoolschedule.R;

import java.util.ArrayList;
import java.util.List;

public class TermDetailActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog2;
    private Button startDateButton;
    private Button endDateButton;
    Repository repository;
    private TextView TermNameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_term_detail2);
        repository = new Repository(getApplication());
        startDateButton = findViewById(R.id.DateStartPickerbutton);
        endDateButton = findViewById(R.id.DateEndPickerbutton);
        TermNameField = findViewById(R.id.textTermNameField);
        initDatePicker();
        initDatePicker2();
        if(getIntent().getStringExtra("id") != null){
            fillForm(getIntent().getStringExtra("id"));
        }
        else{
            fillForm();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        loadRecycler();
        return true;
    }
    public void onResume() {
        loadRecycler();
        super.onResume();

    }

    private void fillForm(String id) {
        String test = String.valueOf(getIntent().getStringExtra("termName"));
        TermNameField.setText(test);
        startDateButton.setText(String.valueOf(getIntent().getStringExtra("startTermDate")));
        endDateButton.setText(String.valueOf(getIntent().getStringExtra("endTermDate")));
        loadRecycler();

    }

    private void loadRecycler() {
        if(getIntent().getStringExtra("id")!=null) {
            List<ClassEntity> allClasses = repository.getAllClasses();
            RecyclerView recyclerView = findViewById(R.id.termClassList);
            int TermID = Integer.parseInt(getIntent().getStringExtra("id"));
            List<ClassEntity> listClasses = new ArrayList<ClassEntity>();
            int i = 0;
            while (i < allClasses.size()) {
                if (allClasses.get(i).getTermID() == TermID) {
                    listClasses.add(allClasses.get(i));
                }
                i = i + 1;
            }
            ClassAdapter classAdapter = new ClassAdapter(this, listClasses);
            recyclerView.setAdapter(classAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }
    }

    private void fillForm() {
        startDateButton.setText(DateHelper.getTodaysDate());
        endDateButton.setText(DateHelper.getTodaysDate());
    }


    public void dateStartPicker(View view) {
        datePickerDialog.show();
    }

    public void dateEndPicker(View view) {
        datePickerDialog2.show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = DateHelper.makeDateString(dayOfMonth, month, year);
                startDateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog =  new DatePickerDialog(this,style, dateSetListener,year,month,day);
    }
    private void initDatePicker2() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = DateHelper.makeDateString(dayOfMonth, month, year);
                endDateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog2 =  new DatePickerDialog(this,style, dateSetListener,year,month,day);
    }

    public void saveTerm(View view) {
        int termID;
        String termName = String.valueOf(TermNameField.getText());
        String startDate = String.valueOf(startDateButton.getText());
        String endDate = String.valueOf(endDateButton.getText());
        if(getIntent().getStringExtra("id") != null){
            termID = Integer.parseInt(getIntent().getStringExtra("id"));
            TermEntity current = new TermEntity(termID,termName,startDate,endDate);
            repository.update(current);
        }
        else{
            List<TermEntity> allTerms = repository.getAllTerms();
            termID = allTerms.get(allTerms.size()-1).getTermID()+1;
            TermEntity current = new TermEntity(termID,termName,startDate,endDate);
            repository.insert(current);
        }
        TermEntity current = new TermEntity(termID,termName,startDate,endDate);



        finish();
    }

    public void cancelTerm(View view) {
        finish();
    }

    public void deleteTerm(View view) {
        if(getIntent().getStringExtra("id")!=null){
            int termID = Integer.parseInt(getIntent().getStringExtra("id"));
            List<ClassEntity> allClasses = repository.getAllClasses();
            List<ClassEntity> termClasses = new ArrayList<>();
            int j =0;
            while(j < allClasses.size()) {
                if (allClasses.get(j).getTermID() == termID) {
                    termClasses.add(allClasses.get(j));
                    j = allClasses.size();
                }
                j = j + 1;
            }
            if(termClasses.size()!=0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TermDetailActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Before a term can be deleted, all associated classes and assessments must be deleted.  Delete term with all associated classes and assessments?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteRecursive();
                            }
                        });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                deleteRecursive();
            }
        }
        else{
            finish();
        }
    }

    public void addClass(View view) {
        Intent intent = new Intent(TermDetailActivity.this, ClassDetailActivity.class);
        intent.putExtra("termID", String.valueOf(getIntent().getStringExtra("id")));
        startActivity(intent);

    }

    public void deleteRecursive(){

            int termID = Integer.parseInt(getIntent().getStringExtra("id"));
            TermEntity currentTerm = null;
            List<TermEntity> allTerms = repository.getAllTerms();
            int i = 0;
            while(i < allTerms.size()){
                if(allTerms.get(i).getTermID() == termID){
                    currentTerm = allTerms.get(i);
                    i = allTerms.size();
                }
                i=i+1;
            }
            List<ClassEntity> allClasses = repository.getAllClasses();
            int j =0;
            while(j < allClasses.size()){
                if(allClasses.get(j).getTermID() == termID){
                    ClassEntity currentClass = allClasses.get(j);
                    int classID = currentClass.getClassID();
                    int k = 0;
                    List<AssessmentEntity> allAssessments = repository.getmAllAssesments();
                    while(k < allAssessments.size()){
                        if(allAssessments.get(k).getClassID() == classID){
                            AssessmentEntity currentAssessment = allAssessments.get(k);
                            repository.delete(currentAssessment);
                        }
                        k = k+1;
                    }
                    repository.delete(currentClass);
                }
                j = j+1;
            }
            repository.delete(currentTerm);


        Toast.makeText(TermDetailActivity.this, "Deletion Complete", Toast.LENGTH_LONG).show();
        finish();
    }
}
