package com.acukanov.sml.ui.video.list;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.acukanov.sml.R;
import com.acukanov.sml.ui.base.BaseActivity;
import com.acukanov.sml.utils.ActivityCommon;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoListAcivity extends BaseActivity {
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);
        ActivityCommon.setUpActionBarToolbar(this, mToolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content, VideoListFragment.newInstance())
                    .commit();
        }
    }
}
