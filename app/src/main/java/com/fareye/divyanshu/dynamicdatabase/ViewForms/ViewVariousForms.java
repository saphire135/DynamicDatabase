package com.fareye.divyanshu.dynamicdatabase.ViewForms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fareye.divyanshu.dynamicdatabase.FormMaster;
import com.fareye.divyanshu.dynamicdatabase.FormMasterDB;
import com.fareye.divyanshu.dynamicdatabase.R;

import java.util.ArrayList;

import Adapters.FormAdapterClickListener;
import Adapters.FormViewAdapter;

import static com.fareye.divyanshu.dynamicdatabase.FormMasterDB.KEY_FORM_ID;

public class ViewVariousForms extends AppCompatActivity implements FormAdapterClickListener {

    RecyclerView formRecyclerView;
    public static final String FORMNAME = "form_name";
    public static final String FORMID = "form_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ViewVariousForms", "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_various_forms);

        ArrayList<FormMaster> formMasterArrayList = new FormMasterDB(this).getAllForms();
        formRecyclerView = (RecyclerView) findViewById(R.id.form_recyclerview);
        builFormView(formMasterArrayList);
    }

    public void builFormView(ArrayList<FormMaster> formMastersArrayList) {
        Log.d("FormActivity", "in buildUserView()" + formMastersArrayList.size());
        FormViewAdapter formViewAdapter = new FormViewAdapter(this, formMastersArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        formRecyclerView.setLayoutManager(layoutManager);
        formRecyclerView.setAdapter(formViewAdapter);
    }

    @Override
    public void onClick(View view, final FormMaster formMaster) {
        Log.d("FormActivity", "onClick()");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Welcome !!");
        alertDialog.setCancelable(true);
        alertDialog.setMessage("Do you want to view or add a form ??");
        alertDialog.setNegativeButton("VIEW", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ViewVariousForms.this, ViewFormOfAttributes.class);
                intent.putExtra(KEY_FORM_ID, formMaster.getId());
                Log.d("test()", formMaster.getId() + "");
                startActivity(intent);
            }
        });
        alertDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ViewVariousForms.this, FillAttributes.class);
                Log.d("test()>>>>>>>>>>>>", formMaster.getId() + "");
                intent.putExtra(FORMID, formMaster.getId());
                intent.putExtra(FORMNAME, formMaster.getName());
                startActivity(intent);
            }
        });
        AlertDialog alert1 = alertDialog.create();
        alert1.show();
    }
}