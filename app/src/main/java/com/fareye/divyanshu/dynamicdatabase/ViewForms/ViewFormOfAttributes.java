package com.fareye.divyanshu.dynamicdatabase.ViewForms;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fareye.divyanshu.dynamicdatabase.DTO.FormAttributes;
import com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.FormAttributesTable;
import com.fareye.divyanshu.dynamicdatabase.R;
import com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.FormMasterDB;
import com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.SaveFieldsInDatabase;
import com.fareye.divyanshu.dynamicdatabase.DTO.SaveFieldsTable;

import java.util.ArrayList;

import static com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.FormMasterDB.KEY_FORM_ID;

public class ViewFormOfAttributes extends AppCompatActivity {

    ArrayList<FormAttributes> formAttributesArrayList;
    SaveFieldsInDatabase saveFieldsInDatabase;
    LinearLayout parentLinearLayout;
    ArrayList<SaveFieldsTable> formArrayList;
    EditText[] arraylistOfEdittextFields;
    TextView formAttributeLabelTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        Intent intent = getIntent();

        formAttributesArrayList = new FormAttributesTable(this).getAttributes(intent.getIntExtra(KEY_FORM_ID, 0));
        Log.i("Testing for size", formAttributesArrayList.size() + "");
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
        arraylistOfEdittextFields = new EditText[formArrayList.size()];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            Log.d("arrays length",String.valueOf(arraylistOfEdittextFields.length));
        for (int i = 0; i < arraylistOfEdittextFields.length; i++) {
            arraylistOfEdittextFields[i] = new EditText(this);
            formAttributeLabelTV = new TextView(this);
            arraylistOfEdittextFields[i].setPadding(10, 10, 10, 30);
            arraylistOfEdittextFields[i].setLayoutParams(params);
            formAttributeLabelTV.setPadding(10, 10, 10, 10);
            formAttributeLabelTV.setTypeface(null, Typeface.BOLD_ITALIC);
            formAttributeLabelTV.setLayoutParams(params);
           // Log.d("Testing",formAttributesArrayList.get(i).getType());
            if (formAttributesArrayList.get(i).getType().equals("number")) {
                arraylistOfEdittextFields[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (formAttributesArrayList.get(i).getType().equals("string")) {
                arraylistOfEdittextFields[i].setInputType(InputType.TYPE_CLASS_TEXT);
            } else if (formAttributesArrayList.get(i).getType().equals("text")) {
                arraylistOfEdittextFields[i].setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                arraylistOfEdittextFields[i].setLines(3);
            }
            formAttributeLabelTV.setText(formAttributesArrayList.get(i).getLabel());
            arraylistOfEdittextFields[i].setText(formArrayList.get(i).getFormAttributeValue());
            parentLinearLayout.addView(formAttributeLabelTV);
            parentLinearLayout.addView(arraylistOfEdittextFields[i]);
        }
    }

    public void updateButton(View view) {
        Log.d("ViewFormActivity", "updateButton");
        boolean fieldNotEmpty = true;

        ArrayList<SaveFieldsTable> formArrayList = new ArrayList<>();
        int attributeIndex = 0;
        if (arraylistOfEdittextFields != null) {
            for (EditText attributeEditText : arraylistOfEdittextFields) {
                if (attributeEditText.getText().length() == 0) {
                    fieldNotEmpty = false;
                } else {
                    FormAttributes attributes = formAttributesArrayList.get(attributeIndex);
                    SaveFieldsTable form = new SaveFieldsTable(Integer.parseInt(attributes.getId()), attributeEditText.getText().toString());
                    formArrayList.add(form);
                    attributeIndex++;
                }
            }
            if (fieldNotEmpty) {
                Log.d("ViewFormActivity", "Edit Text fields are not empty");
                FormMasterDB formMasterDB = new FormMasterDB(this);
                if (formMasterDB.updateForm(formArrayList)) {
                    Toast.makeText(this, "Form updated successfully!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Log.d("AddFormActivity", "Error savinf form");
                    Toast.makeText(this, "Error saving form!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "One or more field is empty!", Toast.LENGTH_LONG).show();
            }
        }
    }

}