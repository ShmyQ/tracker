package com.example.williamschymik.tracker;

public class DataLog {

    public DataLog(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    private String type;
    private String value;
}
