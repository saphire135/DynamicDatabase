package com.fareye.divyanshu.dynamicdatabase.DTO;

import java.util.ArrayList;

/**
 * Created by diyanshu on 14/7/17.
 */

public class FormMaster {

    public int id;
    public String name;
    ArrayList<FormAttributes> formMaster;

    public ArrayList<FormAttributes> getFormMaster() {
        return formMaster;
    }

    public void setFormMaster(ArrayList<FormAttributes> formMaster) {
        this.formMaster = formMaster;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}