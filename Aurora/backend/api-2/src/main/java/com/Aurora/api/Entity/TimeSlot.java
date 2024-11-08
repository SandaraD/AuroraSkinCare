package com.Aurora.api.Entity;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TimeSlot {
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters
    public DayOfWeek getDay() { return day; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    public void setDay(DayOfWeek day) { this.day = day; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}