package com.acukanov.sml.data.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.acukanov.data.model.Results;
import com.acukanov.sml.utils.LogUtils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import rx.Scheduler;

@Singleton
public class DatabaseHelper implements DatabaseService {
    private static final String LOG_TAG = LogUtils.makeLogTag(DatabaseHelper.class);
    private BriteDatabase mDb;

    @Inject
    public DatabaseHelper(Context context, Scheduler scheduler) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(context);
        mDb = sqlBrite.wrapDatabaseHelper(databaseOpenHelper, scheduler);
        mDb.setLoggingEnabled(true);
    }

    @Override
    public Observable<List<Results>> getTopRated() {
        return requestAllRecordsForTable(DatabaseTables.TopRatedTable.TABLE_NAME);
    }

    @Override
    public Observable<List<Results>> getPopular() {
        return requestAllRecordsForTable(DatabaseTables.PopularTable.TABLE_NAME);
    }

    @Override
    public Observable<List<Results>> getTopRatedWithOffset(int page) {
        return requestWithOffset(DatabaseTables.TopRatedTable.TABLE_NAME, page);
    }

    @Override
    public Observable<List<Results>> getPopularWithOffset(int page) {
        return requestWithOffset(DatabaseTables.PopularTable.TABLE_NAME, page);
    }

    @Override
    public Observable<Results> getTopRatedById(int id) {
        return getById(id, DatabaseTables.TopRatedTable.TABLE_NAME);
    }

    @Override
    public Observable<Results> getPopularById(int id) {
        return getById(id, DatabaseTables.PopularTable.TABLE_NAME);
    }

    @Override
    public Observable<List<Long>> saveTopRated(List<Results> results) {
        return saveRecords(DatabaseTables.TopRatedTable.TABLE_NAME, results);
    }

    @Override
    public Observable<List<Long>> savePopular(List<Results> results) {
        return saveRecords(DatabaseTables.PopularTable.TABLE_NAME, results);
    }

    @Override
    public void cleanTopRated() {
        clearDatabaseTable(DatabaseTables.TopRatedTable.TABLE_NAME);
    }

    @Override
    public void cleanPopular() {
        clearDatabaseTable(DatabaseTables.PopularTable.TABLE_NAME);
    }

    // region private methods

    private Observable<List<Results>> requestAllRecordsForTable(String tableName) {
        return Observable.create(subscriber -> {
            List<Results> results = new ArrayList<>();
            String query = "SELECT * FROM " + tableName;
            Cursor cursor = mDb.query(query);
            while (cursor.moveToNext()) {
                results.add(DatabaseTables.parseCursor(cursor));
            }
            cursor.close();
            subscriber.onNext(results);
        });
    }

    private Observable<List<Results>> requestWithOffset(String tableName, int page) {
        return Observable.create(subscriber -> {
            List<Results> results = new ArrayList<>();
            String query = null;
            if (page != 1) {
                query = "SELECT * FROM " + tableName
                        + " LIMIT " + (page * 20) + ", 20";
            } else {
                query = "SELECT * FROM " + tableName
                        + " LIMIT 0, 20";
            }
            Cursor cursor = mDb.query(query);
            while (cursor.moveToNext()) {
                results.add(DatabaseTables.parseCursor(cursor));
            }
            cursor.close();
            subscriber.onNext(results);
        });
    }

    private Observable<List<Long>> saveRecords(String tableName, List<Results> results) {
        return Observable.create(subscriber -> {
            List<Long> ids = new ArrayList<>();
            List<ContentValues> contentValues = DatabaseTables.createOrUpdate(results);
            try {
                for (ContentValues cv : contentValues) {
                    long id = mDb.insert(tableName, cv);
                    ids.add(id);
                }
                subscriber.onNext(ids);
            } catch (Exception e) {
                subscriber.onError(e);
                LogUtils.error(LOG_TAG, e.getMessage());
            }
        });
    }

    private Observable<Results> getById(int id, String tableName) {
        return Observable.create(subscriber -> {
            Results result = new Results();
            String query = "SELECT * FROM " + tableName
                    + " WHERE " + DatabaseTables.COLUMN_NAME_ID + " = " + id;
            Cursor cursor = mDb.query(query);
            while (cursor.moveToNext()) {
                result = DatabaseTables.parseCursor(cursor);
            }
            cursor.close();
            subscriber.onNext(result);
        });
    }

    private void clearDatabaseTable(String tableName) {
        mDb.delete(tableName, null, null);
    }

    // endregion
}
