package com.fareye.divyanshu.dynamicdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddYourJson extends AppCompatActivity {

    EditText jsonLink;
    Button  goButton;
    Button viewButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_json);

        goButton = (Button) findViewById(R.id.button);
        viewButton = (Button) findViewById(R.id.button2);
        jsonLink = (EditText) findViewById(R.id.editText);

        String url = "http://192.168.1.52:8090/home";
        ConnectionEstablishment established = new ConnectionEstablishment();
        established.doInBackground(url,"a","b");

    }
}
