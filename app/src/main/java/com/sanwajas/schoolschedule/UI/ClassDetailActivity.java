package com.sanwajas.schoolschedule.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
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

public class ClassDetailActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog2;
    private Button startDateButton;
    private Button endDateButton;
    private TextView name;
    private TextView classNotes;
    private TextView teacherName;
    private TextView teacherPhone;
    private TextView teacherEMail;
    private int classID;
    private Spinner term;
    private Spinner status;

    String id = null;
    ArrayAdapter<String> adapter;
    Repository repository = new Repository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        startDateButton = findViewById(R.id.DateStartPickerbutton);
        endDateButton = findViewById(R.id.DateEndPickerbutton);
        name = findViewById(R.id.textClassNameField);
        classNotes = findViewById(R.id.classNote);
        teacherName = findViewById(R.id.instructorName);
        teacherPhone = findViewById(R.id.instructorPhone);
        teacherEMail = findViewById(R.id.instructorEMail);
        term = findViewById(R.id.TermList);
        status = findViewById(R.id.Status);
        initDatePicker();
        initDatePicker2();
        List<String> spinnerArray = getAllTermNames();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        term.setAdapter(adapter);

        if(getIntent().getStringExtra("id") != null){
            this.id = getIntent().getStringExtra("id");
            fillForm(id);
        }
        else if(getIntent().getStringExtra("termID") != null){
            try {
                fillForm();
                String stringTermID = String.valueOf(getIntent().getStringExtra("termID"));
                List<TermEntity> mAllTerms = repository.getAllTerms();
                int TermID = Integer.parseInt(stringTermID);
                int i=0;
                while(i<mAllTerms.size()){
                    if(mAllTerms.get(i).getTermID() == TermID){
                        term.setSelection(adapter.getPosition(mAllTerms.get(i).getTermName()));
                        int test = adapter.getPosition(mAllTerms.get(i).getTermName());
                        i = mAllTerms.size();
                    }
                    i=i+1;
                }


            }catch(Exception ignored){
            }
        }
        else{
            fillForm();
        }
    }

    public void onResume() {
        loadRecycler();
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu_with_notification, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.snotification:

                // set notification for start of class
                String className = name.getText().toString();
                String dateFromButton = startDateButton.getText().toString();
                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR)-1900;
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH)-1;
                Date yesterday = new Date(year,month,day,23,59);
                Date myDate = null;
                try{
                    myDate =  simpleDateFormat.parse(dateFromButton);
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(myDate.after(yesterday)) {
                    Long trigger = myDate.getTime();
                    Intent intent = new Intent(ClassDetailActivity.this, TheReceiver.class);
                    intent.putExtra("key", "Class " + className + " begins on " + dateFromButton + ".");
                    PendingIntent sender = PendingIntent.getBroadcast(ClassDetailActivity.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                }
                return true;

            case R.id.enotification:

                //Set notification for end of class
                String className2 = name.getText().toString();
                String dateFormat2 = "MM/dd/yyyy";
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(dateFormat2, Locale.US);
                Calendar cal2 = Calendar.getInstance();
                int year2 = cal2.get(Calendar.YEAR)-1900;
                int month2 = cal2.get(Calendar.MONTH);
                int day2 = cal2.get(Calendar.DAY_OF_MONTH)-1;
                Date yesterday2 = new Date(year2,month2,day2,23,59);

                String dateFromButton2 = endDateButton.getText().toString();
                Date myDate2 = null;
                try{
                    myDate2 =  simpleDateFormat2.parse(dateFromButton2);
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(myDate2.after(yesterday2)) {
                    Long trigger2 = myDate2.getTime();
                    Intent intent2 = new Intent(ClassDetailActivity.this, TheReceiver.class);
                    intent2.putExtra("key", "Class " + className2 + " ends on " + dateFromButton2 + ".");
                    PendingIntent sender2 = PendingIntent.getBroadcast(ClassDetailActivity.this, ++MainActivity.numAlert, intent2, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                }
                return true;
            case R.id.refresh:
                loadRecycler();
                return true;
        }
        return true;
    }


    private void fillForm() {
        startDateButton.setText(DateHelper.getTodaysDate());
        endDateButton.setText(DateHelper.getTodaysDate());
    }

    private void fillForm(String id) {
        name.setText(String.valueOf(getIntent().getStringExtra("name")));
        classNotes.setText(String.valueOf(getIntent().getStringExtra("notes")));
        teacherName.setText(String.valueOf(getIntent().getStringExtra("instructorName")));
        teacherPhone.setText(String.valueOf(getIntent().getStringExtra("instructorPhone")));
        teacherEMail.setText(String.valueOf(getIntent().getStringExtra("instructorEMail")));
        startDateButton.setText(String.valueOf(getIntent().getStringExtra("start")));
        endDateButton.setText(String.valueOf(getIntent().getStringExtra("end")));
        String stringTermID = String.valueOf(getIntent().getStringExtra("term"));
        List<TermEntity> mAllTerms = repository.getAllTerms();
        int TermID = Integer.parseInt(stringTermID);
        int i=0;
        while(i<mAllTerms.size()){
            if(mAllTerms.get(i).getTermID() == TermID){
                term.setSelection(adapter.getPosition(mAllTerms.get(i).getTermName()));
                int test = adapter.getPosition(mAllTerms.get(i).getTermName());
                i = mAllTerms.size();
            }
            i=i+1;
        }
        String classStatus = String.valueOf(getIntent().getStringExtra("status"));
        String[] stringArray = getResources().getStringArray(R.array.Class_Status);
        status.setSelection(Arrays.asList(stringArray).indexOf(classStatus));
        loadRecycler();


    }

    private void loadRecycler() {
        List<AssessmentEntity> allAssessments = repository.getmAllAssesments();
        RecyclerView recyclerView = findViewById(R.id.classAssessmentList);
        List<AssessmentEntity> classAssessments = new ArrayList<AssessmentEntity>();
        try {
            int cID = Integer.parseInt(getIntent().getStringExtra("id"));
            int j = 0;
            while (j < allAssessments.size()) {
                if (allAssessments.get(j).getClassID() == cID) {
                    AssessmentEntity current = allAssessments.get(j);
                    classAssessments.add(current);
                }
                j = j + 1;
            }
        }catch(Exception Ignore){

        }
        AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this, classAssessments);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<String> getAllTermNames() {
        ArrayList<String> termNames = new ArrayList<String>();
        List<TermEntity> allTerms = repository.getAllTerms();
        int i = 0;
        while (i < allTerms.size()) {
            termNames.add(allTerms.get(i).getTermName());
            i = i + 1;
        }
        return termNames;
    }


    public void saveClass(View view) {
        String sStart = String.valueOf(startDateButton.getText());
        String sEnd = String.valueOf(endDateButton.getText());
        String sName = String.valueOf(name.getText());
        String sClassNotes = String.valueOf(classNotes.getText());
        String sTeacherName = String.valueOf(teacherName.getText());
        String sTeacherPhone = String.valueOf(teacherPhone.getText());
        String sTeacherEMail = String.valueOf(teacherEMail.getText());
        String sTermString = term.getSelectedItem().toString();
        String sStatus = status.getSelectedItem().toString();
        int sTerm = 0;
        List<TermEntity> mAllTerms = repository.getAllTerms();
        int i = 0;
        while(i < mAllTerms.size()){
            if(mAllTerms.get(i).getTermName().equals(sTermString)){
                sTerm = mAllTerms.get(i).getTermID();
                i=mAllTerms.size();
            }
            i=i+1;
        }

        if(id != null){
            int sClassID = Integer.parseInt(id);
            ClassEntity currentClass = new ClassEntity(sClassID,sName,sStart,sEnd,sStatus,sTeacherName,sTeacherPhone,sTeacherEMail,sClassNotes,sTerm);
            repository.update(currentClass);
        }
        else{
            List<ClassEntity> mAllClasses = repository.getAllClasses();
            int sClassID = mAllClasses.get(mAllClasses.size()-1).getClassID()+1;
            ClassEntity currentClass = new ClassEntity(sClassID,sName,sStart,sEnd,sStatus,sTeacherName,sTeacherPhone,sTeacherEMail,sClassNotes,sTerm);
            repository.insert(currentClass);
        }
        finish();

    }

    public void cancelClass(View view) {
        finish();
    }

    public void deleteClass(View view) {

        if(id !=null){
            int dClassID = Integer.parseInt(id);
            List<ClassEntity> mAllClasses = repository.getAllClasses();
            ClassEntity current = null;
            int i = 0;
            while(i<mAllClasses.size()){
                if(dClassID == mAllClasses.get(i).getClassID()){
                    current = mAllClasses.get(i);
                    i = mAllClasses.size();
                }
                i=i+1;
            }
            List<AssessmentEntity> dAssessments = repository.getmAllAssesments();
            List<AssessmentEntity> classAssessments = new ArrayList<>();
            int j = 0;
            while(j < dAssessments.size()){
                if(dAssessments.get(j).getClassID() == dClassID){
                    classAssessments.add(dAssessments.get(j));
                    j = dAssessments.size();
                }
                j=j+1;
            }
            if(classAssessments.size()!=0){
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassDetailActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Before a class can be deleted, all associated assessments must be deleted.  Delete class with all associated assessments?");
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

    public void shareNote(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String note = String.valueOf(classNotes.getText());
        String className = String.valueOf(name.getText());
        String shareBody = className+": "+note;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, className+" notes");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void addAssessment(View view) {
        Intent intent = new Intent(ClassDetailActivity.this, AssessmentDetailActivity.class);
        intent.putExtra("classID",String.valueOf(getIntent().getStringExtra("id")));
        startActivity(intent);
    }

    public void deleteRecursive(){

        if(id !=null){
            int dClassID = Integer.parseInt(id);
            List<ClassEntity> mAllClasses = repository.getAllClasses();
            ClassEntity current = null;
            int i = 0;
            while(i<mAllClasses.size()){
                if(dClassID == mAllClasses.get(i).getClassID()){
                    current = mAllClasses.get(i);
                    i = mAllClasses.size();
                }
                i=i+1;
            }
            List<AssessmentEntity> dAssessments = repository.getmAllAssesments();
            int j = 0;
            while(j < dAssessments.size()){
                if(dAssessments.get(j).getClassID() == dClassID){
                    AssessmentEntity currentAssessment = dAssessments.get(j);
                    repository.delete(currentAssessment);
                }
                j=j+1;
            }
            repository.delete(current);
        }
        Toast.makeText(ClassDetailActivity.this, "Deletion Complete", Toast.LENGTH_LONG).show();
        finish();
    }
}