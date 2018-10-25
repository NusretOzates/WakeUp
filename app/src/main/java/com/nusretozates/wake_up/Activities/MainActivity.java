package com.nusretozates.wake_up.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nusretozates.wake_up.Adapters.AlarmAdapter;
import com.nusretozates.wake_up.R;
import com.nusretozates.wake_up.Utils.Alarm;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Calendar c = Calendar.getInstance();
    private TextView textView;
    private TextView textView2;
    private Button newAlarmButton;
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
                Intent i = new Intent(MainActivity.this, SetAlarmActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.screen_toleft, R.anim.screen_toright);
            }
        });


        recyclerView = findViewById(R.id.alarm_list);
        AlarmAdapter alarmAdapter = new AlarmAdapter(this, Alarm.alarms);
        recyclerView.setAdapter(alarmAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        9999);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

    }


    //Update textview(time) every second.
    class UpdateTimeTask extends TimerTask {
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
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
            });

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.screen_toleft, R.anim.screen_toright);
    }
}

