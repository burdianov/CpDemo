package com.crackncrunch.cpdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crackncrunch.cpdemo.data.NationContract.NationEntry;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText etCountry, etContinent, etWhereToUpdate, etNewContinent,
            etQueryRowById, etWhereToDelete;
    private Button btnInsert, btnUpdate, btnDelete, btnQueryRowById,
            btnDisplayAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCountry = (EditText) findViewById(R.id.etCountry);
        etContinent = (EditText) findViewById(R.id.etContinent);
        etWhereToUpdate = (EditText) findViewById(R.id.etWhereToUpdate);
        etNewContinent = (EditText) findViewById(R.id.etUpdateContinent);
        etQueryRowById = (EditText) findViewById(R.id.etQueryByRowId);
        etWhereToDelete = (EditText) findViewById(R.id.etWhereToDelete);

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnQueryRowById = (Button) findViewById(R.id.btnQueryById);
        btnDisplayAll = (Button) findViewById(R.id.btnDisplayAll);

        btnInsert.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnQueryRowById.setOnClickListener(this);
        btnDisplayAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInsert:
                insert();
                break;
            case R.id.btnUpdate:
                update();
                break;
            case R.id.btnDelete:
                delete();
                break;
            case R.id.btnQueryById:
                queryRowById();
                break;
            case R.id.btnDisplayAll:
                queryAndDisplayAll();
                break;
        }
    }

    private void insert() {
        String countryName = etCountry.getText().toString();
        String continentName = etContinent.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NationEntry.COLUMN_COUNTRY, countryName);
        contentValues.put(NationEntry.COLUMN_CONTINENT, continentName);

        Uri uri = NationEntry.CONTENT_URI;
        Uri uriRowInserted = getContentResolver().insert(uri, contentValues);
        Log.i(TAG, "Items inserted at: " + uriRowInserted);
    }

    private void update() {
        String whereToUpdate = etWhereToUpdate.getText().toString();
        String newContinent = etNewContinent.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NationEntry.COLUMN_CONTINENT, newContinent);
        String selection = NationEntry.COLUMN_COUNTRY + " = ?";
        String[] selectionArgs = {whereToUpdate};

        Uri uri = NationEntry.CONTENT_URI;
        int rowsUpdated = getContentResolver().update(uri, contentValues,
                selection, selectionArgs);
    }

    private void delete() {
        String countryName = etWhereToDelete.getText().toString();
        String selection = NationEntry.COLUMN_COUNTRY + " = ?";
        String[] selectionArgs = {countryName};

        Uri uri = Uri.withAppendedPath(NationEntry.CONTENT_URI, countryName);
        int rowsDeleted = getContentResolver().delete(uri, selection,
                selectionArgs);
    }

    private void queryRowById() {
        String rowId = etQueryRowById.getText().toString();

        String[] projection = {
                NationEntry._ID,
                NationEntry.COLUMN_COUNTRY,
                NationEntry.COLUMN_CONTINENT
        };
        String selection = NationEntry._ID + " = ? ";
        String[] selectionArgs = {rowId};

        String sortOrder = null;

        Uri uri = Uri.withAppendedPath(NationEntry.CONTENT_URI, rowId);

        Cursor cursor = getContentResolver().query(uri, projection,
                selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.moveToNext()) {
            String string = "";

            String[] columns = cursor.getColumnNames();
            for (String column : columns) {
                string += "\t" + cursor.getString(cursor.getColumnIndex(column));
            }
            string += "\n";

            cursor.close();
        }
    }

    private void queryAndDisplayAll() {
        String[] projection = {
                NationEntry._ID,
                NationEntry.COLUMN_COUNTRY,
                NationEntry.COLUMN_CONTINENT
        };
        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Uri uri = NationEntry.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, projection, selection,
                selectionArgs,
                sortOrder);

        if (cursor != null) {
            String string = "";
            while (cursor.moveToNext()) {
                String[] columns = cursor.getColumnNames();
                for (String column : columns) {
                    string += "\t" + cursor.getString(cursor.getColumnIndex(column));
                }
                string += "\n";
            }
            cursor.close();
        }
    }
}
