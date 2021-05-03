package com.example.powerhouseapp;

import java.io.Serializable;

public class Outlet implements Serializable {

    private String name;
    private String ip;

    public Outlet(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setName(String name) {
        this.name = name;
    }
}
