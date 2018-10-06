package com.nusretozates.wake_up;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Calendar extends View
{

    /**
     * height, width of the clock's view
     */
    private int mHeight, mWidth = 0;

    /**
     * numeric numbers to denote the hours
     */
    private int[] dayofMonths = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};

    /**
     * spacing and padding of the clock-hands around the clock round
     */
    private int mPadding = 0;
    private int mNumeralSpacing = 0;
    private Paint mPaint;
    private Rect mRect = new Rect();
    private boolean isInit;  // it will be true once the clock will be initialized.


    public Calendar(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        if (!isInit) {
            mPaint = new Paint();
            mHeight = getHeight();
            mWidth = getWidth();
            mPadding = mNumeralSpacing + 50;  // spacing from the circle border
            isInit = true;  // set true once initialized
        }

        /** draw the canvas-color */
        canvas.drawColor(Color.rgb(229, 229, 229));

        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);
        canvas.drawRect(0,0,mWidth,mHeight/3,mPaint);

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);
        canvas.drawRect(0,mHeight/3,mWidth,mHeight,mPaint);
        postInvalidateDelayed(500);
        invalidate();




    }
}
