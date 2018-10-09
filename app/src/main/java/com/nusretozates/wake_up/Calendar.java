package com.nusretozates.wake_up;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

public class Calendar extends View
{

    /**
     * height, width of the clock's view
     */
    private int mHeight, mWidth = 0;

    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slider_saat);

    private Paint mPaint;
    int drawingpic_x = 0, drawingpic_y = 0;
    int drawingPicWidth;
    int drawingPicHeight;
    int drawingPicOffset_x = 0, drawingPicOffset_y = 0;
    float a = 0;
    private float x, y;
    private boolean isInit, touching, drawingTouch;  // it will be true once the clock will be initialized.




    public Calendar(Context context) {
        super(context);
    }

    public Calendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
        canvas.drawColor(Color.rgb(229, 229, 229));

        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(40);
        canvas.drawRoundRect(25f, 25f, ((float) mWidth - 35), ((float) mHeight - 35), 50f, 50f, mPaint);

        mPaint.reset();

        canvas.drawBitmap(bitmap, 25f + a - ((mWidth * 25 / 100) / 2), 25f, mPaint);
        postInvalidateDelayed(500);
        invalidate();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_BUTTON_PRESS:
                drag
        }
        invalidate();
        return true;

    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        int action = event.getAction();
        if (action == DragEvent.ACTION_DRAG_ENTERED) {
            a = event.getX();
        }
    }
}
