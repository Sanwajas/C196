package com.sanwajas.schoolschedule.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanwajas.schoolschedule.Database.Repository;
import com.sanwajas.schoolschedule.Entities.AssessmentEntity;
import com.sanwajas.schoolschedule.Entities.ClassEntity;
import com.sanwajas.schoolschedule.R;

import java.util.List;

public class ClassActivity extends AppCompatActivity {
    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        loadRecycler();

    }

    public void onResume() {
        loadRecycler();
        super.onResume();

    }

    private void loadRecycler() {
        repository = new Repository(getApplication());
        List<ClassEntity> allClasses = repository.getAllClasses();
        RecyclerView recyclerView = findViewById(R.id.allClassList);
        ClassAdapter classAdapter = new ClassAdapter(this, allClasses);
        recyclerView.setAdapter(classAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        repository = new Repository(getApplication());
        List<ClassEntity> allClasses = repository.getAllClasses();
        RecyclerView recyclerView = findViewById(R.id.allClassList);
        ClassAdapter classAdapter = new ClassAdapter(this, allClasses);
        recyclerView.setAdapter(classAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return true;
    }



    public void addClass(View view) {
        startActivity(new Intent(ClassActivity.this, ClassDetailActivity.class));

    }
}