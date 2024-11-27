package com.example.group7_project;

public class Data {
    private String title;
    private String desc;
    private String icon;

    public Data(String title, String desc, String icon){
        this.icon = icon;
        this.title = title;
        this.desc = desc;
    }
    public String getTitle(){
        return title;
    }
    public String getDesc(){
        return desc;
    }
    public String getIcon() {
        return icon;
    }
}
