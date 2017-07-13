package com.fareye.divyanshu.dynamicdatabase;

import android.os.AsyncTask;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
/**
 * Created by diyanshu on 13/7/17.
 */




class ConnectionEstablishment extends AsyncTask<String, String, String> {

    ProgressBar loadingDatabase;
    Users[] getdata = new Users[2];
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        loadingDatabase = (ProgressBar) R.id.loading;
        loadingDatabase.setVisibility(View.VISIBLE);

    }

    @Override
    protected String doInBackground(String... voids) {
        Log.d("ConnectionEstablishment", "In doInBAckground()");//Logs are used to show what to know whether it got success or it get failed.
        String email = voids[0];//Creating  a string.
        // Do some validation here


        URL url = null;//Object of url class.
        HttpURLConnection urlConnection = null;//Object of httpurlconnection class.

        try {
            url = new URL(email);//String conversion in url type.
            urlConnection = (HttpURLConnection) url.openConnection();//Urlconnection opened.
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //Users the BufferedInputStream in the form of Inputstream.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            //Storing all the elements in stringbuilder.
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            Log.d("JSON String",stringBuilder.toString());
            bufferedReader.close();//Closing BufferedReader.
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            getdata = new Users[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {
                getdata[i] = Users.fromJson(jsonArray.getJSONObject(i));
            }

             return stringBuilder.toString();

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);//Logs are used to show what to know whether it got success or it get failed.
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();//urlconnection disconnected.
        }
        return null;
    }


    protected void onPostExecute(String response) {


    }
}
