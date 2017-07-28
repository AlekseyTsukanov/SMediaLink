package com.acukanov.sml.data.db;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.acukanov.sml.utils.LogUtils;

import javax.inject.Inject;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = LogUtils.makeLogTag(DatabaseOpenHelper.class);
    private static final String DATABASE_NAME = "themovie.db";
    private static final int VERSION = 1;
    private Context mContext;

    @Inject
    DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            LogUtils.debug(LOG_TAG, DatabaseTables.TopRatedTable.CREATE_TABLE);
            db.execSQL(DatabaseTables.TopRatedTable.CREATE_TABLE);
            LogUtils.debug(LOG_TAG, DatabaseTables.PopularTable.CREATE_TABLE);
            db.execSQL(DatabaseTables.PopularTable.CREATE_TABLE);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            LogUtils.error(LOG_TAG, e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
