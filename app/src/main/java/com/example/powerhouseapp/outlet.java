package com.example.powerhouseapp;

public class outlet {

    //This class needs to be fully fleshed out
    private String name;
    private String ip;

    public outlet(String name, String ip) {
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
