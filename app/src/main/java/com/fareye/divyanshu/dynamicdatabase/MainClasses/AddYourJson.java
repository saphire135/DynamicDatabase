package com.fareye.divyanshu.dynamicdatabase.MainClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fareye.divyanshu.dynamicdatabase.R;
import com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.FormAttributesTable;
import com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.FormMasterDB;
import com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.SaveFieldsInDatabase;
import com.fareye.divyanshu.dynamicdatabase.ViewForms.ViewVariousForms;

import static com.fareye.divyanshu.dynamicdatabase.TablesOfDatabase.FormMasterDB.DATABASE_NAME;

public class AddYourJson extends AppCompatActivity {

    EditText jsonLink;
    Button goButton;
    Button viewButton;
    String url = "";
    SQLiteDatabase sqLiteDatabase;
    FormMasterDB formMasterDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_json);

        SharedPreferences prefs = this.getSharedPreferences("counters", this.MODE_PRIVATE);
        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        boolean flag = prefs.getBoolean("counter", false);
        if (!flag) {
            Toast.makeText(this, "Database created first time only", Toast.LENGTH_SHORT).show();
            new SaveFieldsInDatabase(this).onUpgrade(sqLiteDatabase, 0, 0);
            new FormMasterDB(this).onUpgrade(sqLiteDatabase, 0, 0);
            new FormAttributesTable(this).onUpgrade(sqLiteDatabase, 0, 0);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("counter", true);
            editor.apply();
        }

        goButton = (Button) findViewById(R.id.button);
        viewButton = (Button) findViewById(R.id.button2);
        jsonLink = (EditText) findViewById(R.id.editText);

        //formMasterDB = new FormMasterDB(this);
        //  sqLiteDatabase = formMasterDB.getWritableDatabase();

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = jsonLink.getText().toString();

                if (URLUtil.isValidUrl(url)) {

                    Log.d("GoButton is working", url);

                    ConnectionEstablishment established = new ConnectionEstablishment(AddYourJson.this, sqLiteDatabase);
                    established.execute(url);
                    Log.d("Strings", "Connection established successfully");
                } else
                    Toast.makeText(AddYourJson.this, "Please Enter a Valid LINK", Toast.LENGTH_SHORT).show();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                FormMasterDB fdb = new FormMasterDB(AddYourJson.this);
//                ArrayList<FormMaster> ase = new ArrayList<FormMaster>();
//                ase = fdb.getAllForms();
//                Log.d("Arraylist",ase.toString());
                Log.d("ViewButton is working.", "ADD/VIEW");
                startActivity(new Intent(AddYourJson.this, ViewVariousForms.class));
            }
        });
    }
}