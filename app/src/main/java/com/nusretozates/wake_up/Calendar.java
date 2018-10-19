package com.nusretozates.wake_up;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Calendar extends View
{

    /**
     * height, width of the clock's view
     */
    private int mHeight, mWidth = 0;
    private Activity myactivity;

    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slider_saat);

    private Paint mPaint;

    float a = 0;
    private boolean isInit, dragstarted = false;  // it will be true once the clock will be initialized.




    public Calendar(Context context) {
        super(context);
    }

    public Calendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Calendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Calendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setActivity(Activity activity) {
        myactivity = activity;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (!isInit) {
            mPaint = new Paint();
            mHeight = getHeight();
            mWidth = getWidth();
            bitmap = Bitmap.createScaledBitmap(bitmap, mWidth * 25 / 100, mHeight - 60, false);
            isInit = true;  // set true once initialized

        }

        /** draw the canvas-color */
        canvas.drawColor(Color.TRANSPARENT);

        mPaint.reset();
        mPaint.setColor(Color.rgb(229, 229, 229));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(80);
        canvas.drawRoundRect(25f, 25f, ((float) mWidth - 35), ((float) mHeight - 35), 50f, 50f, mPaint);

        mPaint.reset();

        if (dragstarted) {
            canvas.drawBitmap(bitmap, 25f + a - ((mWidth * 25 / 100) / 2), 25f, mPaint);
        } else {
            canvas.drawBitmap(bitmap, 25f, 25f, mPaint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() > 25 && event.getX() < mWidth * 25 / 100) {
                    dragstarted = true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                a = event.getX();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:


                if (event.getX() < mWidth - mWidth * 25 / 100) {
                    dragstarted = false;
                    invalidate();
                } else {
                    AlarmRecieverActivity.mMediaPlayer.stop();
                    myactivity.finishAndRemoveTask();
                }


                break;
        }

        invalidate();
        return true;

    }


}
