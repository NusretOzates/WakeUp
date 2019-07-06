package com.nusretozates.wake_up.Activities

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.nusretozates.wake_up.R
import com.nusretozates.wake_up.Utils.Alarm
import java.util.*


class SetAlarmActivity : AppCompatActivity() {

    private var datePicker: DatePicker? = null
    private var timePicker: TimePicker? = null
    private var button: Button? = null
    private var day: Int = 0
    private var month: Int = 0
    private var year: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.screen_toleft, R.anim.screen_toright)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_magic_word)
        datePicker = findViewById(R.id.takvim)
        timePicker = findViewById(R.id.timepicker)
        button = findViewById(R.id.button)
        val dateselectorDrawable = getDrawable(R.drawable.date_selector_design)
        val hour_selector_drawable = getDrawable(R.drawable.hour_selector_design)

        val c = Calendar.getInstance()

        datePicker!!.minDate = c.timeInMillis
        timePicker!!.hour = c.time.hours
        timePicker!!.minute = c.time.minutes


        button!!.background = dateselectorDrawable
        button!!.setOnClickListener {
            if (button!!.background === dateselectorDrawable) {

                day = datePicker!!.dayOfMonth
                month = datePicker!!.month
                year = datePicker!!.year
                Toast.makeText(this@SetAlarmActivity, day.toString() + " " + month + " " + year, Toast.LENGTH_SHORT).show()

                var animation = AnimationUtils.loadAnimation(this@SetAlarmActivity, R.anim.screen_toright)
                datePicker!!.animation = animation
                datePicker!!.animate()
                datePicker!!.visibility = View.INVISIBLE
                button!!.background = hour_selector_drawable

                timePicker!!.visibility = View.VISIBLE
                animation = AnimationUtils.loadAnimation(this@SetAlarmActivity, R.anim.screen_toleft)
                timePicker!!.animation = animation
                timePicker!!.animate()

            } else {
                hour = timePicker!!.hour
                minute = timePicker!!.minute
                val cal = Calendar.getInstance()
                cal.set(year, month, day, hour, minute)
                //Create a new PendingIntent and add it to the AlarmManager
                val intent = Intent(this@SetAlarmActivity, AlarmRecieverActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(this@SetAlarmActivity,
                        alarmID, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                val am = getSystemService(Activity.ALARM_SERVICE) as AlarmManager
                am.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis,
                        pendingIntent)

                val saat = hour.toString() + " : " + minute.toString()
                val tarih = day.toString() + "/" + month.toString() + "/" + year.toString()

                Alarm.alarms.add(Alarm(saat, tarih, alarmID.toString()))
                alarmID++
                val i = Intent(this@SetAlarmActivity, MainActivity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.screen_toleft, R.anim.screen_toright)

            }
        }

    }

    companion object {
        private var alarmID = 1000
    }
}
