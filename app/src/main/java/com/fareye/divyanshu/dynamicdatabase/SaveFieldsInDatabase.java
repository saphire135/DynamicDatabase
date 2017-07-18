package com.fareye.divyanshu.dynamicdatabase;

import android.content.Context;
import android.widget.EditText;

import java.util.ArrayList;

public class SaveFieldsInDatabase {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "databaseforms";

    // Contacts table name
    public String TABLE_Fields = "Entered_Fields";

     Context context;
        ArrayList<EditText> AttributesInEditText = new ArrayList<EditText>();

    public SaveFieldsInDatabase(Context context, ArrayList<EditText> AttributesInEditText) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private static final String KEY_FORM_ID = "id";
//
//    String CREATE_FORMS_TABLE = "CREATE TABLE " + TABLE_Fields + "("
//            + KEY_FORM_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
//    static FormAttributesTable fat = new FormAttributesTable(context);


}
