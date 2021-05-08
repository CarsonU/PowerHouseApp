package com.example.powerhouseapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A zone
 */
public class Zone implements Serializable {
    private String name;
    private ArrayList<Outlet> outlets;

    /**
     * Zone constructor
     * @param name The name of the zone
     * @param outlets The list of outlets in the zone
     */
    public Zone(String name, ArrayList<Outlet> outlets) {
        this.name = name;
        this.outlets = outlets;
    }

    /**
     * Returns the name of the zone
     * @return the name of the zone
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the zone
     * @param name the name of the zone
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the outlets in the zone
     * @return the outlets in the zone
     */
    public ArrayList<Outlet> getOutlets() {
        return outlets;
    }

}
