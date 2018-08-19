package com.example.williamschymik.tracker;

import java.util.List;

public class DataPage {

    static public class DataPageComponent {

        public DataPageComponent(int position, String title, String type, String value) {
            this.position = position;
            this.title = title;
            this.type = type;
            this.value = value;
        }

        public String getDataType() {
            return title.toLowerCase().replace(' ', '_');
        }

        public int position;
        public String title;
        public String type;
        public String value;
    }

    public DataPage(String name, List<DataPageComponent> components) {
        this.name = name;
        this.components = components;
    }

    public String name;
    public List<DataPageComponent> components;
}
