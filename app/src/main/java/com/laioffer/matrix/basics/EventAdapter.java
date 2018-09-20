package com.laioffer.matrix.basics;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.laioffer.matrix.R;
import com.laioffer.matrix.basics.DataService;
import com.laioffer.matrix.basics.Event;

import java.util.List;

public class EventAdapter extends BaseAdapter {

    Context mContext;
    List<Event> mEvents;
    private int mSelectedIndex;

    public EventAdapter(Context context) {
        this.mContext = context;
        mEvents = DataService.getEventData();
        mSelectedIndex = -1;
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Object getItem(int i) {
        return mEvents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.event_list_item, viewGroup, false);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.event_thumbnail);
        TextView eventTitle = (TextView) view.findViewById(R.id.event_title);
        TextView eventAddress = (TextView) view.findViewById(R.id.event_address);
        TextView eventDescription = (TextView) view.findViewById(R.id.event_description);

        Event event = mEvents.get(i);
        eventTitle.setText(event.getTitle());
        eventAddress.setText(event.getAddress());
        eventDescription.setText(event.getDescription());

        if (i % 3 == 0) {
            imageView.setImageResource(R.drawable.apple);
        } else if (i % 3 == 1) {
            imageView.setImageResource(R.drawable.orange);
        } else {
            imageView.setImageResource(R.drawable.banana);
        }

        if (i == mSelectedIndex) {
            view.setBackgroundColor(Color.GREEN);
        } else {
            view.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        return view;
    }

    public void setSelectedIndex(int selectedIndex) {
        mSelectedIndex = selectedIndex;
    }
}
