package com.laioffer.matrix;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {

    private static final String TAG = "FragmentComm";

    private GridView mGridView;
    private EventAdapter mEventAdapter;

    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        mGridView = (GridView) view.findViewById(R.id.comment_grid);
        mEventAdapter = new EventAdapter(getActivity());
        mGridView.setAdapter(mEventAdapter);

        return view;
    }

    // Change background color if the item is selected
    public void onItemSelected(int position) {
        mEventAdapter.setSelectedIndex(position);
        mEventAdapter.notifyDataSetChanged();
    }
}
