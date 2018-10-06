package com.nusretozates.wake_up;

import java.util.ArrayList;
import java.util.Objects;

public class Alarm
{
    private String alarmsaat;
    private String alarmgun;
    private String alarmID;
    private int imageID = R.drawable.minisaat;
    public static  ArrayList<Alarm> alarms = new ArrayList<>();



    public Alarm(String alarmsaat, String alarmgun, String alarmID) {
        this.alarmsaat = alarmsaat;
        this.alarmgun = alarmgun;
        this.alarmID = alarmID;
    }

    public String getAlarmsaat() {
        return alarmsaat;
    }

    public void setAlarmsaat(String alarmsaat) {
        this.alarmsaat = alarmsaat;
    }

    public String getAlarmgun() {
        return alarmgun;
    }

    public void setAlarmgun(String alarmgun) {
        this.alarmgun = alarmgun;
    }

    public String getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(String alarmID) {
        this.alarmID = alarmID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public void addAlarm(Alarm alarm)
    {

        alarms.add(alarm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        return Objects.equals(alarmsaat, alarm.alarmsaat) &&
                Objects.equals(alarmgun, alarm.alarmgun) &&
                Objects.equals(alarmID, alarm.alarmID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(alarmsaat, alarmgun, alarmID);
    }
}
