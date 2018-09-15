package com.laioffer.matrix;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.UUID;

public class GridViewActivity extends SingleFragmentActivity {

    private static final String TAG = "GridViewActivity";

    public static final String EXTRA_EVENT_ID =
            "com.laioffer.matrix.event_id";

    public static Intent newIntent(Context packageContext, Integer eventId) {
        Intent intent = new Intent(packageContext, GridViewActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, eventId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Integer eventId = (Integer) getIntent()
                .getSerializableExtra(EXTRA_EVENT_ID);
        return GridViewFragment.newInstance(eventId);
    }
}
