package com.crackncrunch.cpdemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.crackncrunch.cpdemo.data.NationContract.NationEntry;

public class NationDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "nations.db";
    public static final int DATABASE_VERSION = 1;

    final String SQL_CREATE_COUNTRY_TABLE
            = "CREATE TABLE " + NationEntry.TABLE_NAME
            + "(" + NationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NationEntry.COLUMN_COUNTRY + " TEXT NOT NULL, "
            + NationEntry.COLUMN_CONTINENT + " TEXT);";

    public NationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NationEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
