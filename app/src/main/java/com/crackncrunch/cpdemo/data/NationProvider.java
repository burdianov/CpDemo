package com.crackncrunch.cpdemo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.crackncrunch.cpdemo.data.NationContract.CONTENT_AUTHORITY;
import static com.crackncrunch.cpdemo.data.NationContract.NationEntry;
import static com.crackncrunch.cpdemo.data.NationContract.NationEntry.TABLE_NAME;
import static com.crackncrunch.cpdemo.data.NationContract.PATH_COUNTRIES;

public class NationProvider extends ContentProvider {
    public static final String TAG = NationProvider.class.getSimpleName();

    public static final int COUNTRIES = 1;
    public static final int COUNTRIES_COUNTRY_NAME = 2;
    public static final int COUNTRIES_ID = 3;

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private NationDbHelper databaseHelper;

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_COUNTRIES, COUNTRIES);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_COUNTRIES + "/#", COUNTRIES_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_COUNTRIES + "/*", COUNTRIES_COUNTRY_NAME);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new NationDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[]
                                selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case COUNTRIES:
                cursor = database.query(
                        TABLE_NAME, projection, null, null,
                        null, null, sortOrder);
                break;
            case COUNTRIES_ID:
                selection = NationEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris
                        .parseId(uri))};
                cursor = database.query(
                        NationEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;
            default:
                throw new IllegalArgumentException(TAG + " Query unknown URI:"
                        + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (uriMatcher.match(uri)) {
            case COUNTRIES:
                return insertRecord(uri, contentValues, TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + " Insert unknown " +
                        "URI: " + uri);
        }
    }

    private Uri insertRecord(Uri uri, ContentValues contentValues, String tableName) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        long rowId = database.insert(tableName, null, contentValues);
        if (rowId == -1) {
            Log.e(TAG, "Insert error for URI: " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable
            String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {
            case COUNTRIES:
                return deleteRecord(null, null, TABLE_NAME);
            case COUNTRIES_ID:
                selection = NationEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris
                        .parseId(uri))};
                return deleteRecord(selection, selectionArgs, TABLE_NAME);
            case COUNTRIES_COUNTRY_NAME:
                selection = NationEntry.COLUMN_COUNTRY + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                return deleteRecord(selection, selectionArgs, TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + " delete unknown " +
                        "URI: " + uri);
        }
    }

    private int deleteRecord(String selection, String[] selectionArgs, String tableName) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsDeleted = database.delete(tableName, selection,
                selectionArgs);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selection, @Nullable String[]
                              selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case COUNTRIES:
                return updateRecord(contentValues, selection, selectionArgs,
                        NationEntry.TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + " update unknown " +
                        "URI: " + uri);
        }
    }

    private int updateRecord(ContentValues contentValues, String selection,
                             String[] selectionArgs, String tableName) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        int rowsUpdated = database.update(tableName,
                contentValues, selection, selectionArgs);
        return rowsUpdated;
    }
}
