package com.example.hm;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.migration.Migration;

@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date; // Format: "yyyy-MM-dd"
    public String eventName;
    public String location; // Add this line for the location

}
