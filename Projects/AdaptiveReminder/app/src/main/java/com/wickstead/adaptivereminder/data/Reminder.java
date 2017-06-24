package com.wickstead.adaptivereminder.data;

public class Reminder {

    final private long id;
    final private String name;
    final private int estimatedPeriodMins;

    public Reminder(long id, String name, int estimatedPeriodMins) {
        this.id = id;
        this.name = name;
        this.estimatedPeriodMins = estimatedPeriodMins;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getEstimatedPeriodMins() {
        return estimatedPeriodMins;
    }
}