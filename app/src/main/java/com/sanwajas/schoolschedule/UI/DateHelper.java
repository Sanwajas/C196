package com.sanwajas.schoolschedule.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.DatePicker;



public class DateHelper {
    DatePickerDialog datePickerDialog;

    public static String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return month+"/"+day+"/"+year;
    }

    public static String makeDateString(int day, int month, int year){
        return month + "/"+day+"/"+year;
    };

    public void datePicker(View view) {
        datePickerDialog.show();
    }

}
