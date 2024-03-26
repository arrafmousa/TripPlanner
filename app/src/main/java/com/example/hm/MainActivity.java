package com.example.hm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CalendarView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String date = year + "-" + (month + 1) + "-" + dayOfMonth; // Adjust month value
            Intent intent = new Intent(MainActivity.this, DayActivity.class);
            intent.putExtra("date", date);
            startActivity(intent);
        });
    }
}
