package com.Aurora.api.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.Aurora.api.Entity.TimeSlot;

public class SchedulingService {

    public List<TimeSlot> getAvailableTimeSlots(DayOfWeek day) {
        List<TimeSlot> timeSlots = new ArrayList<>();

        // time slots according to clinic's schedule
        if (day == DayOfWeek.MONDAY) {
            timeSlots.addAll(generateTimeSlots(LocalTime.of(10, 0), LocalTime.of(13, 0)));
        } else if (day == DayOfWeek.WEDNESDAY) {
            timeSlots.addAll(generateTimeSlots(LocalTime.of(14, 0), LocalTime.of(17, 0)));
        } else if (day == DayOfWeek.FRIDAY) {
            timeSlots.addAll(generateTimeSlots(LocalTime.of(16, 0), LocalTime.of(20, 0)));
        } else if (day == DayOfWeek.SATURDAY) {
            timeSlots.addAll(generateTimeSlots(LocalTime.of(9, 0), LocalTime.of(13, 0)));
        }
        return timeSlots;
    }

    private List<TimeSlot> generateTimeSlots(LocalTime startTime, LocalTime endTime) {
        List<TimeSlot> slots = new ArrayList<>();
        LocalTime time = startTime;
        while (time.plusMinutes(15).isBefore(endTime.plusMinutes(1))) {
            slots.add(new TimeSlot(null, time, time.plusMinutes(15)));
            time = time.plusMinutes(15);
        }
        return slots;
    }
}



