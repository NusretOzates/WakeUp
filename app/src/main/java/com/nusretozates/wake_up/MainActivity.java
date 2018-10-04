package com.nusretozates.wake_up;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Calendar c = Calendar.getInstance();
    private TextView textView;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.saat);
        textView2 = findViewById(R.id.dakika);
        Timer timer = new Timer();
        UpdateTimeTask timeTask = new UpdateTimeTask();
        timer.schedule(timeTask, 1000, 1000);

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

