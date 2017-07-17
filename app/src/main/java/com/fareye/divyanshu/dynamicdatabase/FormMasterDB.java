package com.fareye.divyanshu.dynamicdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by divyanshu on 7/7/17.
 */

public class FormMasterDB extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "databaseforms";

    // Contacts table name
    public String TABLE_FORMS = "forms";


    // Contacts Table Columns names
    private static final String KEY_FORM_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ATTRIBUTES = "formMaster";

    static Context context;

    public FormMasterDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    String CREATE_FORMS_TABLE = "CREATE TABLE " + TABLE_FORMS + "("
            + KEY_FORM_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
    static FormAttributesTable fat = new FormAttributesTable(context);
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_FORMS_TABLE);
        db.execSQL(fat.FROM_ATTRIBUTE_DATABASE_CREATE);

}


    public void addFormMaster(SQLiteDatabase sqLiteDatabase, FormMaster formMaster) {
        //  onCreate(sqLiteDatabase);
        Log.d("FormMasterDB", "in addFormMaster()");
        Log.d("Table",CREATE_FORMS_TABLE);
        ContentValues values = new ContentValues();
        values.put(KEY_FORM_ID, formMaster.getId());
        values.put(KEY_NAME, formMaster.getName());
        // Inserting Row
        sqLiteDatabase.insert(TABLE_FORMS, null, values);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORMS);
        // Create tables again
        onCreate(db);
    }

    public ArrayList<FormMaster> getAllForms() {
        Log.d("UserDBHelper", "in getAllUsers()");
        String countQuery = "SELECT  * FROM " + TABLE_FORMS;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        ArrayList<FormMaster> formAttributesArrayList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                FormMaster formMaster = new FormMaster();
                String idString = cursor.getString(cursor.getColumnIndex(KEY_FORM_ID));
                formMaster.setId(Integer.parseInt(idString));
                formMaster.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));

                formAttributesArrayList.add(formMaster);
            }
        }
        return formAttributesArrayList;
    }
}