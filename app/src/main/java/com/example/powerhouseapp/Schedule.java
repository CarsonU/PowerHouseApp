package com.example.powerhouseapp;

/**
 * A schedule
 */
public class Schedule {
    public String name;
    public String time;

    /**
     * Schedule constructor
     * @param name The name of the schedule
     * @param time The time of the schedule
     */
    public Schedule(String name, String time) {
        this.name = name;
        this.time = time;
    }

    /**
     * Name of schedule getter
     * @return the name of the schedule
     */
    public String getName() {
        return name;
    }

    /**
     * Name of the schedule setter
     * @param name the name of the schedule
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the time of the schedule
     * @return
     */
    public String getTime() {
        return time;
    }
}
