package com.fareye.divyanshu.dynamicdatabase.DTO;

/**
 * Created by diyanshu on 18/7/17.
 */

public class SaveFieldsTable {
    int fromAttributeId;
    String formAttributeValue;

    public SaveFieldsTable(int fromAttributeId, String formAttributeValue) {
        this.fromAttributeId = fromAttributeId;
        this.formAttributeValue = formAttributeValue;
    }

    public SaveFieldsTable() {

    }

    public int getFromAttributeId() {
        return fromAttributeId;
    }

    public void setFromAttributeId(int fromAttributeId) {
        this.fromAttributeId = fromAttributeId;
    }

    public String getFormAttributeValue() {
        return formAttributeValue;
    }

    public void setFormAttributeValue(String formAttributeValue) {
        this.formAttributeValue = formAttributeValue;
    }
}
