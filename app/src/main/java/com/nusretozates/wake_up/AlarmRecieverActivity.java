package com.nusretozates.wake_up;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import java.io.IOException;

public class AlarmRecieverActivity extends AppCompatActivity {
    public static MediaPlayer mMediaPlayer;
    private PowerManager.WakeLock wl;
    private VideoView videoView;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myapp: Mywakelogtag");
        wl.acquire();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_alarm_reciever);

        videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sunrise);
        videoView.setVideoURI(uri);
        calendar = findViewById(R.id.slider);
        calendar.setActivity(this);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        videoView.seekTo(0);
                        videoView.start();
                    }
                });
            }
        });


       // makeDialog();
        playSound(this, getAlarmUri());

        Button stopAlarm = findViewById(R.id.stopAlarm);
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                mMediaPlayer.stop();
                finishAndRemoveTask();
            }
        });

    }
    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }
    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wl.release();
    }

    private void makeDialog()
    {
        final  EditText text = new EditText(this);
        text.setHint("Write the password here ");

        RandomStringGenerator generator = new RandomStringGenerator();
        final String randomText = generator.generate(7);
        final AlertDialog.Builder stop = new AlertDialog.Builder(this);
        stop.setTitle("Wake- Up!");
        stop.setMessage("Prove that you are awake ! Write the password : " + randomText);
        stop.setView(text);

        stop.setPositiveButton("Uyandım!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mMediaPlayer.stop();
                finishAndRemoveTask();
            }
        });

        final Button button = stop.show().getButton(AlertDialog.BUTTON_POSITIVE);
        button.setEnabled(false);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                button.setEnabled(text.getText().toString().equals(randomText));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                button.setEnabled(text.getText().toString().equals(randomText));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
