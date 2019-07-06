package com.nusretozates.wake_up.Activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView

import com.nusretozates.wake_up.Adapters.AlarmAdapter
import com.nusretozates.wake_up.R
import com.nusretozates.wake_up.Utils.Alarm
import java.util.*

class MainActivity : AppCompatActivity() {

    internal var c = Calendar.getInstance()
    private var textView: TextView? = null
    private var textView2: TextView? = null
    private var newAlarmButton: Button? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.saat)
        textView2 = findViewById(R.id.dakika)
        val timer = Timer()
        val timeTask = UpdateTimeTask()
        timer.schedule(timeTask, 1000, 1000)
        newAlarmButton = findViewById(R.id.button2)
        newAlarmButton!!.setOnClickListener {
            val i = Intent(this@MainActivity, SetAlarmActivity::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.screen_toleft, R.anim.screen_toright)
        }


        recyclerView = findViewById(R.id.alarm_list)
        val alarmAdapter = AlarmAdapter(this, Alarm.alarms)
        recyclerView!!.adapter = alarmAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

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
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        9999)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

    }


    //Update textview(time) every second.
    internal inner class UpdateTimeTask : TimerTask() {
        override fun run() {

            runOnUiThread {
                val c = Calendar.getInstance()
                if (c.time.hours < 10) {
                    textView!!.text = "0" + c.time.hours.toString()
                } else
                    textView!!.text = c.time.hours.toString()
                if (c.time.minutes < 10) {
                    textView2!!.text = "0" + c.time.minutes.toString()
                } else
                    textView2!!.text = c.time.minutes.toString()
            }



        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.screen_toleft, R.anim.screen_toright)
    }
}

