package com.nusretozates.wake_up;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class SettingMagicWordActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button button;
    private int day,month,year,hour,minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_magic_word);
        datePicker = findViewById(R.id.takvim);
        timePicker = findViewById(R.id.timepicker);
        button = findViewById(R.id.button);



        Calendar  c = Calendar.getInstance();

        datePicker.setMinDate(c.getTimeInMillis());
        timePicker.setHour(c.getTime().getHours());
        timePicker.setMinute(c.getTime().getMinutes());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (button.getText().equals("Tarihi Seç"))
               {
                   day = datePicker.getDayOfMonth();
                   month = datePicker.getMonth();
                   year = datePicker.getYear();
                   Toast.makeText(SettingMagicWordActivity.this, day + " " + month + " " + year, Toast.LENGTH_SHORT).show();
                   timePicker.setVisibility(View.VISIBLE);
                   datePicker.setVisibility(View.INVISIBLE);
                   button.setText("Saati Seç");
               }else
               {
                   hour = timePicker.getHour();
                   minute = timePicker.getMinute();
                   Calendar cal = Calendar.getInstance();
                  cal.set(year,month,day,hour,minute);
                   //Create a new PendingIntent and add it to the AlarmManager
                   Intent intent = new Intent(SettingMagicWordActivity.this, AlarmRecieverActivity.class);
                   PendingIntent pendingIntent = PendingIntent.getActivity(SettingMagicWordActivity.this,
                           12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                   AlarmManager am =
                           (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                   am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                           pendingIntent);
                    finishAndRemoveTask();
               }
            }
        });




    }
}
