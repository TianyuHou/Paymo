package com.tianyuhou.paymo;

/**
 * Created by Yu on 10/15/2017.
 */

public class Cost {
    private String name;
    private float amount;

    public Cost(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
