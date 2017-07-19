package com.fareye.divyanshu.dynamicdatabase.ViewForms;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fareye.divyanshu.dynamicdatabase.FormAttributes;
import com.fareye.divyanshu.dynamicdatabase.FormAttributesTable;
import com.fareye.divyanshu.dynamicdatabase.R;
import com.fareye.divyanshu.dynamicdatabase.SaveFieldsInDatabase;
import com.fareye.divyanshu.dynamicdatabase.SaveFieldsTable;

import java.util.ArrayList;

import static com.fareye.divyanshu.dynamicdatabase.FormMasterDB.KEY_FORM_ID;

public class ViewFormOfAttributes extends AppCompatActivity {

    ArrayList<FormAttributes> formAttributesArrayList;
    SaveFieldsInDatabase saveFieldsInDatabase;
    LinearLayout parentLinearLayout;
    ArrayList<SaveFieldsTable> formArrayList;
    EditText[] formAttributeValueETArray;
    TextView formAttributeLabelTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        Intent intent = getIntent();

        formAttributesArrayList = new FormAttributesTable(this).getAttributes(intent.getIntExtra(KEY_FORM_ID, 0));
        Log.i("test()>>>>>>", formAttributesArrayList.size() + "");
        parentLinearLayout = (LinearLayout) findViewById(R.id.form_attribute_layout);
        saveFieldsInDatabase = new SaveFieldsInDatabase(this);
        int startAttributeID = Integer.parseInt(formAttributesArrayList.get(0).getId());
        int lastAttributeId = Integer.parseInt(formAttributesArrayList.get(formAttributesArrayList.size() - 1).getId());
        formArrayList = saveFieldsInDatabase.getForm(startAttributeID, lastAttributeId);
        if (formArrayList.size() > 0)
            buildFormView(formArrayList);
        else {
            Toast.makeText(this, "No form present!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

    }

    private void buildFormView(ArrayList<SaveFieldsTable> formArrayList) {
        Log.d("ViewFormActivity", "buildFormView");
        //Toast.makeText(this, formArrayList.size() + "", Toast.LENGTH_LONG).show();
        formAttributeValueETArray = new EditText[formArrayList.size()];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < formAttributeValueETArray.length; i++) {
            formAttributeValueETArray[i] = new EditText(this);
            formAttributeLabelTV = new TextView(this);
            formAttributeValueETArray[i].setPadding(10, 10, 10, 30);
            formAttributeValueETArray[i].setLayoutParams(params);
            formAttributeLabelTV.setPadding(10, 10, 10, 10);
            formAttributeLabelTV.setTypeface(null, Typeface.BOLD_ITALIC);
            formAttributeLabelTV.setLayoutParams(params);
            if (formAttributesArrayList.get(i).getType().equals("number")) {
                formAttributeValueETArray[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (formAttributesArrayList.get(i).getType().equals("string")) {
                formAttributeValueETArray[i].setInputType(InputType.TYPE_CLASS_TEXT);
            } else if (formAttributesArrayList.get(i).getType().equals("text")) {
                formAttributeValueETArray[i].setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                formAttributeValueETArray[i].setLines(3);
            }
            formAttributeLabelTV.setText(formAttributesArrayList.get(i).getLabel());
            formAttributeValueETArray[i].setText(formArrayList.get(i).getFormAttributeValue());
            parentLinearLayout.addView(formAttributeLabelTV);
            parentLinearLayout.addView(formAttributeValueETArray[i]);
        }
    }

}
