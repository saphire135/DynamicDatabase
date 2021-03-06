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
import android.widget.Toast;

import com.fareye.divyanshu.dynamicdatabase.DTO.FormAttributes;
import com.fareye.divyanshu.dynamicdatabase.DTO.SaveFieldsTable;
import com.fareye.divyanshu.dynamicdatabase.MainClasses.AddYourJson;
import com.fareye.divyanshu.dynamicdatabase.R;
import com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.FormAttributesTable;
import com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.FormMasterDB;
import com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.SaveFieldsInDatabase;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static com.fareye.divyanshu.dynamicdatabase.ViewForms.ViewVariousForms.FORMID;

public class FillAttributes extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    FormMasterDB formMasterDB;
    FormAttributesTable formAttributesTable = new FormAttributesTable(this);
    Cursor cursor;
    LinearLayout ll;
    ScrollView scrl;
    static Context mcontext;
    public ArrayList<EditText> AttributesInEditText;
    public ArrayList<FormAttributes> ArrayOfAttributes = new ArrayList<>();
    SaveFieldsInDatabase saveFieldsInDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_attributes);
        saveFieldsInDatabase = new SaveFieldsInDatabase(this);
        scrl = new ScrollView(this);
        ll = new LinearLayout(this);

        ll.setOrientation(LinearLayout.VERTICAL);
        scrl.addView(ll);

        formMasterDB = new FormMasterDB(this);
        sqLiteDatabase = formMasterDB.getWritableDatabase();

        ArrayOfAttributes = formAttributesTable.getAttributes(getIntent().getIntExtra(FORMID, 0));
        Log.d("Hello", ArrayOfAttributes.toString());
        int startAttributeID = Integer.parseInt(ArrayOfAttributes.get(0).getId());
        int lastAttributeId = Integer.parseInt(ArrayOfAttributes.get(ArrayOfAttributes.size() - 1).getId());
        if (saveFieldsInDatabase.getAttributeCursor(startAttributeID, lastAttributeId).getCount() == 0) {
            generateFormAttributes(ArrayOfAttributes);
        } else {
            Toast.makeText(this, "Form Already Present", Toast.LENGTH_SHORT).show();
        }

    }

    public void generateFormAttributes(ArrayList<FormAttributes> ArrayOfAttributes) {
        Log.d("Adding", "in generateformview()" + ArrayOfAttributes.size());
        AttributesInEditText = new ArrayList<EditText>();

        for (int i = 0; i < ArrayOfAttributes.size(); i++) {
            AttributesInEditText.add(new EditText(this));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 30);
            AttributesInEditText.get(i).setLayoutParams(params);
            AttributesInEditText.get(i).setHint("Your " + ArrayOfAttributes.get(i).getLabel());

            if (ArrayOfAttributes.get(i).getType().equals("number")) {
                AttributesInEditText.get(i).setInputType(InputType.TYPE_CLASS_NUMBER);
            } else {
                AttributesInEditText.get(i).setInputType(InputType.TYPE_CLASS_TEXT);
            }

            ll.addView(AttributesInEditText.get(i));
            Log.d("generateFormView", "Layout added");
        }
        Button add_btn = new Button(this);
        add_btn.setText("Save Form");
        ll.addView(add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            SaveFieldsInDatabase saveFieldsInDatabase = new SaveFieldsInDatabase(FillAttributes.mcontext);

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                saveFieldsInTable();
                Intent intent = new Intent(FillAttributes.this, AddYourJson.class);
                startActivity(intent);
            }
        });

        this.setContentView(scrl);
    }

    public void saveFieldsInTable() {
        Log.d("Adding", "in saveFieldsInTable()");
        boolean fieldNotEmpty = true;

        ArrayList<SaveFieldsTable> formArrayList = new ArrayList<>();
        int attributeIndex = 0;

        if (AttributesInEditText != null) {
            for (EditText attributeEditText : AttributesInEditText) {
                if (attributeEditText.getText().length() == 0) {
                    fieldNotEmpty = false;
                } else {
                    FormAttributes attributes = ArrayOfAttributes.get(attributeIndex);
                    SaveFieldsTable form = new SaveFieldsTable(Integer.parseInt(attributes.getId()), attributeEditText.getText().toString());
                    formArrayList.add(form);
                    attributeIndex++;
                }
            }
            if (fieldNotEmpty) {

                if (saveFieldsInDatabase.insertForm(formArrayList)) {

                }
            } else {
                Toast.makeText(this, "Incomplete entries", LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AddYourJson.class));
        this.finish();
    }
}