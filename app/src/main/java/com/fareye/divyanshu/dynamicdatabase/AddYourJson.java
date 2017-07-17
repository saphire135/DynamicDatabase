package com.fareye.divyanshu.dynamicdatabase;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fareye.divyanshu.dynamicdatabase.ViewForms.ViewVariousForms;

public class AddYourJson extends AppCompatActivity {

    EditText jsonLink;
    Button  goButton;
    Button viewButton;
    String url = "";
    SQLiteDatabase sqLiteDatabase;
    FormMasterDB formMasterDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_json);


        goButton = (Button) findViewById(R.id.button);
        viewButton = (Button) findViewById(R.id.button2);
        jsonLink = (EditText) findViewById(R.id.editText);

         formMasterDB= new FormMasterDB(this);
        sqLiteDatabase = formMasterDB.getWritableDatabase();

        goButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                url = jsonLink.getText().toString();
                Log.d("GoButton is working",url);

                ConnectionEstablishment established = new ConnectionEstablishment(AddYourJson.this,sqLiteDatabase);
               established.execute(url);
                Log.d("Strings","Connection established successfully");
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                FormMasterDB fdb = new FormMasterDB(AddYourJson.this);
//                ArrayList<FormMaster> ase = new ArrayList<FormMaster>();
//                ase = fdb.getAllForms();
//                Log.d("Arraylist",ase.toString());
                Log.d("ViewButton is working.","ADD/VIEW");
                startActivity(new Intent(AddYourJson.this, ViewVariousForms.class));
            }
        });
    }
}