package com.fareye.divyanshu.dynamicdatabase;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by diyanshu on 13/7/17.
 */


class ConnectionEstablishment extends AsyncTask<String, String, String> {

    ProgressDialog progressDialog;
        FormMaster formMaster;
    Context mcontext;
    FormMasterDB formMasterDB = new FormMasterDB(mcontext);
    FormAttributesTable formAttributesTable = new FormAttributesTable(mcontext);
    Gson gson= new Gson();
    GsonBuilder gsonBuilder;
    SQLiteDatabase sqLiteDatabase;

    public ConnectionEstablishment(Context context,SQLiteDatabase sqLiteDatabase){
        mcontext = context;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(mcontext,
                "ProgressDialog",
                "Wait for some seconds");
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

    }

    @Override
    protected String doInBackground(String... voids) {
        Log.d("ConnectionEstablishment", "In doInBAckground()");//Logs are used to show what to know whether it got success or it get failed.
        String link = voids[0];//Creating  a string.
//        // Do some validation here
//
        Log.d("Form Link",link);
        URL url ;//Object of url class.
        HttpURLConnection urlConnection = null;//Object of httpurlconnection class.
//
        try {
            url = new URL(link);//String conversion in url type.
            urlConnection = (HttpURLConnection) url.openConnection();//Urlconnection opened.
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //FormMasterDB the BufferedInputStream in the form of Inputstream.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            //Storing all the elements in stringbuilder.
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            Log.d("JSON String", stringBuilder.toString());
            bufferedReader.close();//Closing BufferedReader.
            String linknew = stringBuilder.toString();
//        String linknew = "{ \"id\": 1, \"name\":\"Home\", \"formMaster\": [ { \"id\": 1, \"label\": \"Firstname\", \"type\": \"string\", \"sequence\": 1 }, { \"id\": 2, \"label\": \"Lastname\", \"type\": \"string\", \"sequence\": 2 }, { \"id\": 3, \"label\": \"Contact\", \"type\": \"number\", \"sequence\": 3 }, { \"id\": 4, \"label\": \"Address\", \"type\": \"text\", \"sequence\":4 } ] }";
            buildUserList(linknew);

            if (linknew != null) {
                 formMaster=  buildUserList(linknew);
                Log.d("linknew","check for formmaster");
                if (formMaster != null) {
                    String status;
                    if (storeData(formMaster)) {
                        status = "Form already present";
                        return status;
                    } else {
                        status = "Form created succesfully";
                        return status;
                    }
                }
            }

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);//Logs are used to show what to know whether it got success or it get failed.
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();//urlconnection disconnected.
        }
        return null;
    }


    protected void onPostExecute(String response) {
        progressDialog.dismiss();
    }

    public FormMaster buildUserList(String response) {
        Log.d("FetchRequestData", "in buildUserList()");
        FormMaster formMaster;
        try {
            JSONObject jsonObject = new JSONObject(response);
                formMaster = gson.fromJson(response,FormMaster.class);
                Log.d("forms id",String.valueOf(formMaster.getId()));
            Log.d("forms name",String.valueOf(formMaster.getName()));
            Log.d("forms formmaster",String.valueOf(formMaster.getFormMaster().get(0)));
            return formMaster;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean storeData(FormMaster formMaster) {
        try {
            Log.d("FetchRequestData", "in storeData()");
            sqLiteDatabase.beginTransactionNonExclusive();
            formMasterDB.addFormMaster(sqLiteDatabase, formMaster);
            formAttributesTable.addFormAttributes(sqLiteDatabase, formMaster.getId(), formMaster.getFormMaster());
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
            Log.d("FetchRequestData", "Saved succesfully()");
            return false;
        } catch (Exception e) {
            Log.e("FetchRequestData", "Exception in storeData()");
            sqLiteDatabase.close();
            //  formMasterDB.testMasterDB();
            // formAttributesDB.testAttributesDB();
            return true;
        }
        finally {

        }
    }
}