package com.crackncrunch.cpdemo;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crackncrunch.cpdemo.data.NationDbHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText etCountry, etContinent, etWhereToUpdate, etNewContinent,
            etQueryRowById, etWhereToDelete;
    private Button btnInsert, btnUpdate, btnDelete, btnQueryRowById,
            btnDisplayAll;

    private SQLiteDatabase database;
    private NationDbHelper databaseHelper;

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

        databaseHelper = new NationDbHelper(this);
        database = databaseHelper.getWritableDatabase();
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

    }

    private void update() {

    }

    private void delete() {

    }

    private void queryRowById() {

    }

    private void queryAndDisplayAll() {

    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }
}
