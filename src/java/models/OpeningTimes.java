package models;

import java.io.Serializable;
import java.util.Date;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 *
 * @author Firas.Alhawari
 * 
 */
public class OpeningTimes implements Serializable{
    private DayOfWeek dayOfWeek;
    private LocalTime from;
    private LocalTime to;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getFrom() {
        return from;
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public LocalTime getTo() {
        return to;
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }
}