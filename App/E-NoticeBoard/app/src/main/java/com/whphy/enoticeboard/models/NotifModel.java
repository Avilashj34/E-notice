package com.whphy.enoticeboard.models;

/**
 * Created by Jaykishan on 26/7/2017.
 */

public class NotifModel {
    String title,data;

    public NotifModel(String title, String data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
