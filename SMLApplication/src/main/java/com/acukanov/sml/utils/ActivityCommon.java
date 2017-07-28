package com.acukanov.sml.utils;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ActivityCommon {

    public static Toolbar setUpActionBarToolbar(AppCompatActivity activity, int toolbarId) {
        Toolbar actionBarToolbar = (Toolbar) activity.findViewById(toolbarId);
        return setUpActionBarToolbar(activity, actionBarToolbar);
    }

    public static Toolbar setUpActionBarToolbar(AppCompatActivity activity, Toolbar toolbar) {
        if (toolbar != null) {
            activity.setSupportActionBar(toolbar);
        }
        return toolbar;
    }

    public static ActionBar setHomeAsUp(AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        return actionBar;
    }

    public static void showError(Activity activity, int resMessage, String throwableMessage) {
        String message = null;
        if (resMessage != -1) {
            message = activity.getResources().getString(resMessage);
        } else {
            message = throwableMessage;
        }
        DialogFactory.createSimpleOkErrorDialog(activity, message).show();
    }

    public static void startShareIntent(Activity activity, Fragment fragment, String textToShare) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textToShare);
        if (fragment != null) {
            fragment.startActivity(intent);
        } else {
            activity.startActivity(intent);
        }
    }
}
