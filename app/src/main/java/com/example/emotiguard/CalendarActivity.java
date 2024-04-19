package com.example.emotiguard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
private EditText select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        select = findViewById(R.id.select);
        select.setInputType(InputType.TYPE_NULL);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(select);
            }

            private void showDateTimeDialog( EditText select) {
               final Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                               calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE,minute);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                                    select.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        };

                        new TimePickerDialog(CalendarActivity.this,timeSetListener,calendar.get(calendar.HOUR_OF_DAY),calendar.get(calendar.MINUTE),false).show();
                    }
                };
                new DatePickerDialog(CalendarActivity.this,dateSetListener,calendar.get(calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH)).show();
            }
        } );
    }



}