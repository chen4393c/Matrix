package com.laioffer.matrix.basics;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.laioffer.matrix.R;

public class ListViewActivity extends SingleFragmentActivity
        implements ListViewFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return ListViewFragment.newInstance();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_master_detail;
    }

    @Override
    public void onEventSelected(Integer position) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            // phone mode
            Intent intent = GridViewActivity.newIntent(this, position);
            startActivity(intent);
        } else {
            // tablet mode
            Fragment newDetail = GridViewFragment.newInstance(position);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }
}
