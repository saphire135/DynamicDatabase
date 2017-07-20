package com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import com.fareye.divyanshu.dynamicdatabase.DTO.SaveFieldsTable;

import java.util.ArrayList;

public class SaveFieldsInDatabase extends SQLiteOpenHelper {
    //  FillAttributes fillAttributes = new FillAttributes();
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "databaseforms";

    // Contacts table name
    public String TABLE_Fields = "Entered_Fields";
    public static final String FORM_ID = "form_id";
    public static final String FORM_ATTRIBUTE_ID = "form_attr_id";
    public static final String FORM_ATTRIBUTE_VALUE = "form_attr_value";

    //  Context context;
    public static final String FROM_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS Entered_Fields ("
            + FORM_ID + " INTEGER PRIMARY KEY,"
            + FORM_ATTRIBUTE_ID + " TEXT,"
            + FORM_ATTRIBUTE_VALUE + " TEXT);";
    ArrayList<EditText> AttributesInEditText = new ArrayList<EditText>();
    SQLiteDatabase sqLiteDatabase;
    Context context;

    public SaveFieldsInDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(FROM_DATABASE_CREATE);
    }

    public void test() {
        for (SaveFieldsTable form : getALLFormAttributes()) {
            Log.i("in test()>>>>>>>>>", form.getFromAttributeId() + ": " + form.getFormAttributeValue());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("SaveFieldsInDatabase", "in onUpgrade()");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Fields);
        onCreate(sqLiteDatabase);
    }

    // FormMasterDB SaveFieldsInDatabase = new FormMasterDB(context);


    public boolean insertForm(ArrayList<SaveFieldsTable> formArrayList) {
        //SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            //sqLiteDatabase.beginTransactionNonExclusive();
            for (SaveFieldsTable form : formArrayList) {
                ContentValues values = new ContentValues();
                values.put(FORM_ATTRIBUTE_ID, form.getFromAttributeId());
                values.put(FORM_ATTRIBUTE_VALUE, form.getFormAttributeValue());

                sqLiteDatabase.insert(TABLE_Fields, null, values);
            }
            Log.d("insertFrom", "successssssssss");
            // sqLit
            //eDatabase.setTransactionSuccessful();
            test();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }
    }

    public ArrayList<SaveFieldsTable> getForm(int startingAttribute, int lastAttribute) {
        Log.d("FormDB", "in getForm()");
        Cursor cursor = getAttributeCursor(startingAttribute, lastAttribute);
        ArrayList<SaveFieldsTable> formArraylist = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                SaveFieldsTable form = new SaveFieldsTable();
                String idString = cursor.getString(cursor.getColumnIndex(FORM_ATTRIBUTE_ID));
                form.setFromAttributeId(Integer.parseInt(idString));
                form.setFormAttributeValue(cursor.getString(cursor.getColumnIndex(FORM_ATTRIBUTE_VALUE)));
                formArraylist.add(form);
            }
            cursor.close();
            return formArraylist;
        } else {
            return null;
        }
    }

    public Cursor getAttributeCursor(int startingAttributeID, int lastAtributeID) {
        Log.d("FormDB", "in getAttributeCursor()");
        String countQuery = "SELECT  * FROM " + TABLE_Fields + " WHERE " + FORM_ATTRIBUTE_ID + " BETWEEN " + startingAttributeID + " AND " + lastAtributeID;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        // SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);

        // return count
        return cursor;
    }

    public ArrayList<SaveFieldsTable> getALLFormAttributes() {
        Log.d("FormDB", "in getALLFormAttributes()");
        String countQuery = "SELECT * FROM " + TABLE_Fields;
        // SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        ArrayList<SaveFieldsTable> formArraylist = new ArrayList<>();
        //  Log.d("FormDB", cursor.getCount() + "");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                SaveFieldsTable form = new SaveFieldsTable();
                String idString = cursor.getString(cursor.getColumnIndex(FORM_ATTRIBUTE_ID));
                form.setFromAttributeId(Integer.parseInt(idString));
                form.setFormAttributeValue(cursor.getString(cursor.getColumnIndex(FORM_ATTRIBUTE_VALUE)));

                formArraylist.add(form);
            }
            cursor.close();
            return formArraylist;
        } else {
            return null;
        }
    }
}