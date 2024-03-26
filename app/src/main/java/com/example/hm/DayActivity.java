package com.example.hm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DayActivity extends AppCompatActivity {

    private AppDatabase db;
    private EventAdapter adapter;
    private String currentDate;
    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        currentDate = getIntent().getStringExtra("date"); // Get the date passed from MainActivity
//        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "events").allowMainThreadQueries().build();
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name")
                .fallbackToDestructiveMigration() // Add this line
                .build();

        RecyclerView eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EventAdapter(new ArrayList<>());

        databaseExecutor.execute(() -> {
            List<Event> events = db.eventDao().getEventsForDate(currentDate);
            runOnUiThread(() -> {
                adapter.setEvents(events); // Update the adapter with the new list of events
            });
        });
        eventsRecyclerView.setAdapter(adapter);

        FloatingActionButton addEventButton = findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(view -> showAddEventDialog());
    }

    private void showAddEventDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.add_event_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText edittext_event_name = promptView.findViewById(R.id.edittext_event_name);
        final EditText edittext_event_location = promptView.findViewById(R.id.edittext_event_location); // Find the EditText for the event location

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    String eventName = edittext_event_name.getText().toString();
                    String eventLocation = edittext_event_location.getText().toString(); // Get the location from the EditText
                    Event newEvent = new Event();
                    newEvent.date = currentDate;
                    newEvent.eventName = eventName;
                    newEvent.location = eventLocation;
                    databaseExecutor.execute(() -> {
                        db.eventDao().insertEvent(newEvent);
                    });
                    adapter.events.add(newEvent);
                    adapter.notifyItemInserted(adapter.events.size() - 1);
                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
