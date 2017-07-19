package com.fareye.divyanshu.dynamicdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.fareye.divyanshu.dynamicdatabase.ViewForms.FillAttributes;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class SaveFieldsInDatabase extends SQLiteOpenHelper{
        FillAttributes fillAttributes = new FillAttributes();
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "databaseforms";

    // Contacts table name
    public String TABLE_Fields = "Entered_Fields";
    public static final String FORM_ID = "form_id";
    public static final String FORM_ATTRIBUTE_ID = "form_attr_id";
    public static final String FORM_ATTRIBUTE_VALUE = "form_attr_value";

    Context context;
    public static final String FROM_DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS Entered_Fields ("
            + FORM_ID + " INTEGER PRIMARY KEY,"
            + FORM_ATTRIBUTE_ID + " INTEGER,"
            + FORM_ATTRIBUTE_VALUE + " TEXT);";
        ArrayList<EditText> AttributesInEditText = new ArrayList<EditText>();
     SQLiteDatabase sqLiteDatabase;

    public SaveFieldsInDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(FROM_DATABASE_CREATE);
        this.context = context;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("FormDB", "in onUpgrade()");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Fields);
        onCreate(sqLiteDatabase);
    }

    FormMasterDB formDB = new FormMasterDB(context);

    public void saveFieldsInTable() {
        Log.d("AddFormActivity", "in saveFieldsInTable()");
        boolean fieldNotEmpty = true;

        ArrayList<SaveFieldsTable> formArrayList = new ArrayList<>();
        int attributeIndex = 0;

        if (fillAttributes.AttributesInEditText != null) {
            for (EditText attributeEditText : fillAttributes.AttributesInEditText) {
                if (attributeEditText.getText().length() == 0) {
                    fieldNotEmpty = false;
                } else {
                    FormAttributes attributes = fillAttributes.ArrayOfAttributes.get(attributeIndex);
                    SaveFieldsTable form = new SaveFieldsTable(Integer.parseInt(attributes.getId()), attributeEditText.getText().toString());
                    formArrayList.add(form);
                    attributeIndex++;
                }
            }
            if (fieldNotEmpty) {
                Log.d("AddFormActivity", "Edit Text fields are not empty");
                if (insertForm(formArrayList)) {
                    Toast.makeText(context, "Form saved successfully!", LENGTH_SHORT).show();
                    fillAttributes.onBackPressed();
                } else {
                    Log.d("AddFormActivity", "Error saving form");
                    Toast.makeText(context, "Not Saved", LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(context, "One or more field is empty!", LENGTH_LONG).show();
            }
        }}

    public boolean insertForm(ArrayList<SaveFieldsTable> formArrayList) {
        Log.d("FormDB", "in insertForm()");

        // SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
         sqLiteDatabase = this.getWritableDatabase();
        try {
            sqLiteDatabase.beginTransactionNonExclusive();
            for (SaveFieldsTable form : formArrayList) {
                ContentValues values = new ContentValues();
                values.put(FORM_ATTRIBUTE_ID, form.getFromAttributeId());
                values.put(FORM_ATTRIBUTE_VALUE, form.getFormAttributeValue());

                sqLiteDatabase.insert(TABLE_Fields, null, values);
            }
            sqLiteDatabase.setTransactionSuccessful();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }
    }


}