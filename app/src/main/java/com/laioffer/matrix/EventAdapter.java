package com.laioffer.matrix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends BaseAdapter {

    Context context;
    List<Event> eventData;

    public EventAdapter(Context context) {
        this.context = context;
        eventData = DataService.getEventData();
    }

    @Override
    public int getCount() {
        return eventData.size();
    }

    @Override
    public Object getItem(int i) {
        return eventData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.event_list_item, viewGroup, false);
        }

        TextView eventTitle = (TextView) view.findViewById(R.id.event_title);
        TextView eventAddress = (TextView) view.findViewById(R.id.event_address);
        TextView eventDescription = (TextView) view.findViewById(R.id.event_description);

        Event event = eventData.get(i);
        eventTitle.setText(event.getTitle());
        eventAddress.setText(event.getAddress());
        eventDescription.setText(event.getDescription());

        return view;
    }
}
