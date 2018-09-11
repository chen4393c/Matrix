package com.laioffer.matrix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView eventListView = (ListView) findViewById(R.id.event_list_view);

        EventAdapter adapter = new EventAdapter(this);
        eventListView.setAdapter(adapter);
    }
}
