package com.nusretozates.wake_up;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Calendar c = Calendar.getInstance();
    private TextView textView;
    private TextView textView2;
    private Button   newAlarmButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.saat);
        textView2 = findViewById(R.id.dakika);
        Timer timer = new Timer();
        UpdateTimeTask timeTask = new UpdateTimeTask();
        timer.schedule(timeTask, 1000, 1000);
        newAlarmButton = findViewById(R.id.button2);
        newAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SettingMagicWordActivity.class);
                startActivity(i);
            }
        });
        recyclerView = findViewById(R.id.alarm_list);
        AlarmAdapter alarmAdapter = new AlarmAdapter(this,Alarm.alarms);
        recyclerView.setAdapter(alarmAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        AlarmManager manager = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
//        AlarmManager.AlarmClockInfo info = manager.getNextAlarmClock();
//        PendingIntent pendingIntent = info.getShowIntent();
//        int alarmID = pendingIntent.getCreatorUid();
    }

    class UpdateTimeTask extends TimerTask {
        public void run() {
            Calendar c = Calendar.getInstance();
            if (c.getTime().getHours() < 10) {
                textView.setText("0" + String.valueOf(c.getTime().getHours()));
            } else
                textView.setText(String.valueOf(c.getTime().getHours()));
            if (c.getTime().getMinutes() < 10) {
                textView2.setText("0" + String.valueOf(c.getTime().getMinutes()));
            } else
                textView2.setText(String.valueOf(c.getTime().getMinutes()));

        }
    }
}

