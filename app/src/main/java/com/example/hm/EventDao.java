package com.example.hm;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event WHERE date = :date")
    List<Event> getEventsForDate(String date);

    @Insert
    void insertEvent(Event event);

    @Delete
    void deleteEvent(Event event);
}
