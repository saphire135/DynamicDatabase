package com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fareye.divyanshu.dynamicdatabase.DTO.FormAttributes;

import java.util.ArrayList;

/**
 * Created by diyanshu on 16/7/17.
 */

public class FormAttributesTable extends SQLiteOpenHelper {

    public static final String FORM_ATTRIBUTE_TABLE_NAME = "attribute_master";
    public static final String ID = "id";
    public static final String FORM_MASTERID = "form_master_id";
    public static final String LABEL = "label";
    public static final String TYPE = "type";
    public static final String SEQUENCE = "sequence";

    private static final String DATABASE_NAME = "databaseforms";
    private static final int DATABASE_VERSION = 1;


    public static final String FROM_ATTRIBUTE_DATABASE_CREATE = "CREATE TABLE  IF NOT EXISTS " + FORM_ATTRIBUTE_TABLE_NAME
            + " ("
            + ID + " INTEGER PRIMARY KEY,"
            + FORM_MASTERID + " INTEGER,"
            + LABEL + " TEXT,"
            + TYPE + " TEXT,"
            + SEQUENCE + " TEXT);";

    public FormAttributesTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("FormMasterDB", "in onCreate()");
        sqLiteDatabase.execSQL(FROM_ATTRIBUTE_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("FormMasterDB", "in onUpgrade()");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FORM_ATTRIBUTE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void testAttributesDB() {
        for (FormAttributes attributes : getAllAttributes()) {
            Log.d("TEST Attributes >>>>>", attributes.getId() + " " + attributes.getLabel() + " " + attributes.getType() + " " + attributes.getSequence());
        }
    }

    public void addFormAttributes(SQLiteDatabase sqLiteDatabase, int FormMasterId, ArrayList<FormAttributes> formAttributes) {
//        onUpgrade(sqLiteDatabase, DATABASE_VERSION, 0);
        onCreate(sqLiteDatabase);
        Log.d("FormMasterDB", "in addFormAttributes()");
        for (FormAttributes attributes : formAttributes) {
            ContentValues values = new ContentValues();
            values.put(ID, attributes.getId());
            values.put(FORM_MASTERID, FormMasterId);
            values.put(LABEL, attributes.getLabel());
            values.put(TYPE, attributes.getType());
            values.put(SEQUENCE, attributes.getSequence());
            // Inserting Row
            sqLiteDatabase.insertOrThrow(FORM_ATTRIBUTE_TABLE_NAME, null, values);
        }
    }


    public ArrayList<FormAttributes> getAttributes(int id) {
        Log.d("UserDBHelper", "in getAllUsers()");
        String countQuery = "SELECT  * FROM " + FORM_ATTRIBUTE_TABLE_NAME + " WHERE " + FORM_MASTERID + " = " +id ;
        Log.d("CountWuery",countQuery);
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        ArrayList<FormAttributes> ar = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Log.d("Cursor",String.valueOf(cursor.getCount()));
                FormAttributes formAttributes = new FormAttributes();
                String idString = cursor.getString(cursor.getColumnIndex(ID));
                formAttributes.setId(idString);
                formAttributes.setLabel(cursor.getString(cursor.getColumnIndex(LABEL)));
                formAttributes.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
                formAttributes.setSequence(cursor.getString(cursor.getColumnIndex(SEQUENCE)));
                ar.add(formAttributes);
                Log.d("formattributesarraylist",formAttributes.getSequence());
                Log.d("ararraylist",ar.get(0).getLabel());
            }
        }
        return ar;
    }

    public ArrayList<FormAttributes> getAllAttributes() {
        ArrayList<FormAttributes> ar = new ArrayList<>();
        Log.d("UserDBHelper", "in getAllUsers()");
        String countQuery = "SELECT  * FROM " + FORM_ATTRIBUTE_TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = FormAttributesTable.this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                FormAttributes formAttributes = new FormAttributes();
                String idString = cursor.getString(cursor.getColumnIndex(ID));
                formAttributes.setId(idString);
                formAttributes.setLabel(cursor.getString(cursor.getColumnIndex(LABEL)));
                formAttributes.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
                formAttributes.setSequence(cursor.getString(cursor.getColumnIndex(SEQUENCE)));
                ar.add(formAttributes);
            }
        }
        return ar;
    }
}