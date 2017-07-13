package com.fareye.divyanshu.dynamicdatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by diyanshu on 7/7/17.
 */
//A class to get id, title and url.
public class Users {
    public int id;
    public String title;
    public String url;

    Users(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public static Users fromJson(JSONObject jo) {
        Users g = new Users(0, null, null);

        try {
            g.id = jo.getInt("id");
            g.title = jo.getString("title");
            g.url = jo.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return g;
    }
}