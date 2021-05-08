package com.example.powerhouseapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Zone implements Serializable {
    private String name;
    private ArrayList<Outlet> outlets;

    public Zone(String name, ArrayList<Outlet> outlets) {
        this.name = name;
        this.outlets = outlets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Outlet> getOutlets() {
        return outlets;
    }

    public void setOutlets(ArrayList<Outlet> outlets) {
        this.outlets = outlets;
    }
}
