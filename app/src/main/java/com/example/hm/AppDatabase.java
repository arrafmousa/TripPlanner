package com.example.hm;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class}, version = 2) // Increase the version number
public abstract class AppDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
}
