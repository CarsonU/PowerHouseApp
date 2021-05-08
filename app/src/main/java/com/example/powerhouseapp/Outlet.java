package com.example.powerhouseapp;

import java.io.Serializable;

/**
 * An Outlet
 */
public class Outlet implements Serializable {

    private String name;
    private String ip;

    /**
     * Outlet constructor
     * @param name The name of the outlet
     * @param ip The IP Address of the outlet
     */
    public Outlet(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    /**
     * Returns name of the outlet
     * @return name of the outlet
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the IP of the outlet
     * @return the IP of the outlet
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets the name of the outlet
     * @param name name of the outlet
     */
    public void setName(String name) {
        this.name = name;
    }
}
