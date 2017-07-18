package com.fareye.divyanshu.dynamicdatabase.ViewForms;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.fareye.divyanshu.dynamicdatabase.AddYourJson;
import com.fareye.divyanshu.dynamicdatabase.FormAttributes;
import com.fareye.divyanshu.dynamicdatabase.FormAttributesTable;
import com.fareye.divyanshu.dynamicdatabase.FormMasterDB;
import com.fareye.divyanshu.dynamicdatabase.R;

import java.util.ArrayList;

public class FillAttributes extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    FormMasterDB formMasterDB;
    FormAttributesTable formAttributesTable = new FormAttributesTable(this);
    Cursor cursor;
    LinearLayout ll;
    ScrollView scrl;
    static Context mcontext;
    static ArrayList<EditText> AttributesInEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_attributes);

        scrl = new ScrollView(this);
        ll = new LinearLayout(this);

        ll.setOrientation(LinearLayout.VERTICAL);
        scrl.addView(ll);

        formMasterDB = new FormMasterDB(this);
        sqLiteDatabase = formMasterDB.getWritableDatabase();

        ArrayList<FormAttributes> ArrayOfAttributes = formAttributesTable.getAllAttributes();
        Log.d("Hello", ArrayOfAttributes.toString());
        generateFormAttributes(ArrayOfAttributes);

    }

    public void generateFormAttributes(ArrayList<FormAttributes> ArrayOfAttributes) {
        Log.d("AddFormActivity", "in generateformview()" + ArrayOfAttributes.size());
        AttributesInEditText = new ArrayList<EditText>();

        for (int i = 0; i < ArrayOfAttributes.size(); i++) {
            AttributesInEditText.add(new EditText(this));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 30);
            AttributesInEditText.get(i).setLayoutParams(params);
            AttributesInEditText.get(i).setHint("Enter " + ArrayOfAttributes.get(i).getLabel());

            if (ArrayOfAttributes.get(i).getType().equals("number")) {
                AttributesInEditText.get(i).setInputType(InputType.TYPE_CLASS_NUMBER);
            } else {
                AttributesInEditText.get(i).setInputType(InputType.TYPE_CLASS_TEXT);
            }

            ll.addView(AttributesInEditText.get(i));
            Log.d("generateFormView", "Layout added");
        }

        Button add_btn=new Button(this);
        add_btn.setText("Save Form");
        ll.addView(add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


            }
        });


        this.setContentView(scrl);
    }
    @Override
        public void onBackPressed() {
            super.onBackPressed();
            startActivity(new Intent(this, AddYourJson.class));
            this.finish();
        }
}