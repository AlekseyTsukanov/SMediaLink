package com.acukanov.sml.ui.video.details;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.acukanov.data.model.CategoryTypes;
import com.acukanov.sml.R;
import com.acukanov.sml.ui.base.BaseActivity;

public class VideoDetailsActivity extends BaseActivity {
    private static final String EXTRA_VIDEO_ID = "extra_video_id";
    private static final String EXTRA_VIDEO_TYPE = "extra_video_type";

    public static void startActivity(Activity activity, Fragment fragment, int id, CategoryTypes type) {
        Intent intent = new Intent(activity, VideoDetailsActivity.class);
        intent.putExtra(EXTRA_VIDEO_ID, id);
        intent.putExtra(EXTRA_VIDEO_TYPE, type);
        if (fragment != null) {
            fragment.startActivity(intent);
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_no_toolbar);
        if (getIntent() != null) {
            int id = getIntent().getIntExtra(EXTRA_VIDEO_ID, -1);
            CategoryTypes type = (CategoryTypes) getIntent().getSerializableExtra(EXTRA_VIDEO_TYPE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content, VideoDetailsFragment.newInstance(id, type))
                    .commit();
        } else {
            finish();
        }
    }
}
