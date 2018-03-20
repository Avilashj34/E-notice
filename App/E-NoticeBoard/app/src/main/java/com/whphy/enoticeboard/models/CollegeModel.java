package com.whphy.enoticeboard.models;

/**
 * Created by Jaykishan on 26/7/2017.
 */

public class CollegeModel {


    String id;
    public String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;            // What to display in the Spinner list.
    }
}
