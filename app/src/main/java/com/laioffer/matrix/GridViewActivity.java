package com.laioffer.matrix;

import android.support.v4.app.Fragment;

public class GridViewActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return GridViewFragment.newInstance();
    }
}
