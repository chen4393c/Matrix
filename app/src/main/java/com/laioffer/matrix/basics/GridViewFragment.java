package com.laioffer.matrix.basics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.laioffer.matrix.R;

public class GridViewFragment extends Fragment {

    private static final String TAG = "GridViewFragment";

    private static final String ARG_EVENT_ID = "event_id";

    private GridView mGridView;
    private EventAdapter mEventAdapter;

    public static GridViewFragment newInstance(Integer eventId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID, eventId);

        GridViewFragment fragment = new GridViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer eventId = (Integer) getArguments().getSerializable(ARG_EVENT_ID);
        mEventAdapter = new EventAdapter(getContext());
        mEventAdapter.setSelectedIndex(eventId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grid_view, container, false);

        mGridView = v.findViewById(R.id.grid_view);
        mGridView.setAdapter(mEventAdapter);

        return v;
    }
}
