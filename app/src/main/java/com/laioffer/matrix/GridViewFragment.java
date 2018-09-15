package com.laioffer.matrix;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class GridViewFragment extends Fragment {

    private GridView mGridView;
    private EventAdapter mEventAdapter;

    public static GridViewFragment newInstance() {
        return new GridViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grid_view, container, false);

        mGridView = v.findViewById(R.id.grid_view);
        mEventAdapter = new EventAdapter(getContext());
        mGridView.setAdapter(mEventAdapter);

        return v;
    }
}
