package com.sanwajas.schoolschedule.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sanwajas.schoolschedule.Database.Repository;
import com.sanwajas.schoolschedule.Entities.AssessmentEntity;
import com.sanwajas.schoolschedule.Entities.TermEntity;
import com.sanwajas.schoolschedule.R;

import java.util.List;
import java.util.zip.Inflater;

public class TermsActivity extends AppCompatActivity {
    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        loadRecycler();

    }

    private void loadRecycler() {
        repository = new Repository(getApplication());
        List<TermEntity> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.allTermList);
        TermAdapter termAdapter = new TermAdapter(this, allTerms);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        List<TermEntity> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.allTermList);
        TermAdapter TermAdapter = new TermAdapter(this, allTerms);
        recyclerView.setAdapter(TermAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return true;
    }

    public void onResume() {
        loadRecycler();
        super.onResume();

    }

    public void addTerm(View view) {
        startActivity(new Intent(TermsActivity.this, TermDetailActivity.class));
    }
}