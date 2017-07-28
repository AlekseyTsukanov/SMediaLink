package com.acukanov.sml.data.db;


import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.acukanov.data.model.Results;
import com.acukanov.sml.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTables {
    private static final String LOG_TAG = LogUtils.makeLogTag(DatabaseTables.class);
    static final String COLUMN_NAME_ID = "id";
    static final String COLUMN_NAME_TITLE = "title";
    static final String COLUMN_NAME_OVERVIEW = "overview";
    static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
    static final String COLUMN_NAME_POSTER_PATH = "poster_path";
    static final String COLUMN_NAME_RELEASE_DATE = "release_date";
    static final String COLUMN_NAME_BACKDROP = "backdrop";

    public DatabaseTables() {}

    public static abstract class TopRatedTable implements BaseColumns {
        static final String TABLE_NAME = "top_rated";
        static final String CREATE_TABLE = createTable(TABLE_NAME);

        static ContentValues createOrUpdate(Results results) {
            return DatabaseTables.createOrUpdate(results);
        }
    }

    public static abstract class PopularTable implements BaseColumns {
        static final String TABLE_NAME = "popular";
        static final String CREATE_TABLE = createTable(TABLE_NAME);
    }

    private static String createTable(String tableName) {
        return "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY NOT NULL,"
                + COLUMN_NAME_ID + " INTEGER NOT NULL,"
                + COLUMN_NAME_TITLE + " TEXT NOT NULL,"
                + COLUMN_NAME_OVERVIEW + " TEXT NOT NULL,"
                + COLUMN_NAME_VOTE_AVERAGE + " FLOAT NOT NULL,"
                + COLUMN_NAME_POSTER_PATH + " TEXT NOT NULL,"
                + COLUMN_NAME_RELEASE_DATE + " TEXT NOT NULL,"
                + COLUMN_NAME_BACKDROP + " TEXT NOT NULL);";
    }

    static ContentValues createOrUpdate(Results results) {
        ContentValues contentValues = new ContentValues(6);
        contentValues.put(COLUMN_NAME_ID, results.getId());
        contentValues.put(COLUMN_NAME_TITLE, results.getTitle());
        contentValues.put(COLUMN_NAME_OVERVIEW, results.getOverview());
        contentValues.put(COLUMN_NAME_VOTE_AVERAGE, results.getVoteAverage());
        contentValues.put(COLUMN_NAME_POSTER_PATH, results.getPosterPath());
        contentValues.put(COLUMN_NAME_RELEASE_DATE, results.getReleaseDate());
        contentValues.put(COLUMN_NAME_BACKDROP, results.getBackdropPath());
        return contentValues;
    }

    static List<ContentValues> createOrUpdate(List<Results> results) {
        List<ContentValues> contentValues = new ArrayList<>();
        for (Results result : results) {
            contentValues.add(createOrUpdate(result));
        }
        return contentValues;
    }

    static Results parseCursor(Cursor cursor) {
        Results results = new Results();
        results.id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
        results.title = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE));
        results.overview = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OVERVIEW));
        results.voteAverage = cursor.getFloat(cursor.getColumnIndex(COLUMN_NAME_VOTE_AVERAGE));
        results.posterPath = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_POSTER_PATH));
        results.releaseDate = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_RELEASE_DATE));
        results.backdropPath = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_BACKDROP));
        return results;
    }
}
