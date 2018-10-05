package com.nusretozates.wake_up;

import java.util.ArrayList;

public class Alarm
{
    private String alarmsaat;
    private String alarmgun;
    private int alarmID;
    private int imageID = R.drawable.minisaat;
    public static  ArrayList<Alarm> alarms = new ArrayList<>();


    public Alarm(String alarmsaat, String alarmgun, int alarmID) {
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

    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
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
}
