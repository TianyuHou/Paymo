package com.tianyuhou.paymo;

import java.util.Date;

/**
 * Created by Yu on 10/13/2017.
 */

public class Dairy {
    private String title;
    private String desc;
    private String date;
    private String category;
    private float cost;



    public Dairy(String title, String desc, String date, String category,float cost) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.category = category;

        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getCost() {
        return cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
