package com.nusretozates.wake_up.Activities

import android.app.AlertDialog
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.VideoView
import com.nusretozates.wake_up.CustomViews.Slider
import com.nusretozates.wake_up.R
import com.nusretozates.wake_up.Utils.RandomStringGenerator
import java.io.IOException

class AlarmRecieverActivity : AppCompatActivity() {
    private var wl: PowerManager.WakeLock? = null
    private var videoView: VideoView? = null
    private var slider: Slider? = null
    private var secretText: TextView? = null
    private var code: EditText? = null

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private val alarmUri: Uri?
        get() {
            var alert: Uri? = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_ALARM)
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                if (alert == null) {
                    alert = RingtoneManager
                            .getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                }
            }
            return alert
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myapp: Mywakelogtag")
        wl!!.acquire(1 * 60 * 1000L /*10 minutes*/)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            this.setShowWhenLocked(true)
        }

        setContentView(R.layout.activity_alarm_reciever)


        code = findViewById(R.id.codeInput)
        secretText = findViewById(R.id.secretCode)
        videoView = findViewById(R.id.videoView)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.sunrise)
        videoView!!.setVideoURI(uri)
        slider = findViewById(R.id.slider)
        slider!!.setActivity(this)
        slider!!.visibility = View.INVISIBLE

        val generator = RandomStringGenerator()
        val randomText = generator.generate(7)
        secretText!!.text = randomText


        videoView!!.setOnPreparedListener {
            videoView!!.start()
            videoView!!.setOnCompletionListener {
                videoView!!.seekTo(0)
                videoView!!.start()
            }
        }


        // makeDialog();
        playSound(this, alarmUri)


        code!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (code!!.text.toString() == randomText) {
                    slider!!.visibility = View.VISIBLE
                }
            }
        })


    }

    private fun playSound(context: Context, alert: Uri?) {
        mMediaPlayer = MediaPlayer()
        try {
            mMediaPlayer?.setDataSource(context, alert!!)
            val audioManager = context
                    .getSystemService(Context.AUDIO_SERVICE) as AudioManager
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer?.setAudioStreamType(AudioManager.STREAM_ALARM)
                mMediaPlayer?.prepare()
                mMediaPlayer?.start()
                mMediaPlayer?.setOnCompletionListener {
                    mMediaPlayer?.seekTo(0)
                    mMediaPlayer?.start()
                }
            }
        } catch (e: IOException) {
            println("OOPS")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        wl!!.release()
    }

    private fun makeDialog() {
        val text = EditText(this)
        text.hint = "Write the password here "

        val generator = RandomStringGenerator()
        val randomText = generator.generate(7)
        val stop = AlertDialog.Builder(this)
        stop.setTitle("Wake- Up!")
        stop.setMessage("Prove that you are awake ! Write the password : $randomText")
        stop.setView(text)

        stop.setPositiveButton("Uyandım!") { dialogInterface, i ->
            mMediaPlayer?.stop()
            finishAndRemoveTask()
        }

        val button = stop.show().getButton(AlertDialog.BUTTON_POSITIVE)
        button.isEnabled = false
        text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                button.isEnabled = text.text.toString() == randomText
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                button.isEnabled = text.text.toString() == randomText
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    companion object {
        var mMediaPlayer: MediaPlayer? = null
    }
}
