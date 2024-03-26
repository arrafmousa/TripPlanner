package com.example.hm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EventDetailActivity extends AppCompatActivity {

    private TextView selectedFileUriTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        TextView eventName = findViewById(R.id.eventDetailName);
        TextView eventDate = findViewById(R.id.eventDetailDate);
        TextView eventLocation = findViewById(R.id.eventDetailLocation);
        selectedFileUriTextView = findViewById(R.id.selected_file_uri);

        // Get the details from the intent
        String name = getIntent().getStringExtra("eventName");
        String date = getIntent().getStringExtra("eventDate");
        String location = getIntent().getStringExtra("eventLocation");

        // Set the details to the TextViews
        eventName.setText(name);
        eventDate.setText(date);
        eventLocation.setText(location);

        Button selectFileButton = findViewById(R.id.button_select_file);
        selectFileButton.setOnClickListener(view -> openFilePicker());
    }

    public void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri fileUri = data.getData();
            // Display the selected file's URI in the TextView
            selectedFileUriTextView.setText(fileUri.toString());
        }
    }
}