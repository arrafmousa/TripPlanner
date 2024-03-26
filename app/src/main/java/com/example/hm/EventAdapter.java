package com.example.hm;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public List<Event> events;

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(android.R.id.text1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.eventName.setText(event.eventName);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EventDetailActivity.class);
            intent.putExtra("eventName", event.eventName);
            intent.putExtra("eventDate", event.date);
            intent.putExtra("eventLocation", event.location);
            v.getContext().startActivity(intent);
        });
    }

    public void setEvents(List<Event> newEvents) {
        this.events = newEvents;
        notifyDataSetChanged(); // This method tells the RecyclerView to refresh
    }
}
