package com.nusretozates.wake_up.CustomViews

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.nusretozates.wake_up.Activities.AlarmRecieverActivity
import com.nusretozates.wake_up.R

class Slider : View {

    /**
     * height, width of the clock's view
     */
    private var mHeight: Int = 0
    private var mWidth = 0
    private var myactivity: Activity? = null

    internal var bitmap = BitmapFactory.decodeResource(resources, R.drawable.slider_saat)

    private var mPaint: Paint? = null

    internal var a = 0f
    private var isInit: Boolean = false
    private var dragstarted = false  // it will be true once the clock will be initialized.


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setActivity(activity: Activity) {
        myactivity = activity

    }

    override fun onDraw(canvas: Canvas) {

        if (!isInit) {
            mPaint = Paint()
            mHeight = height
            mWidth = width
            bitmap = Bitmap.createScaledBitmap(bitmap, mWidth * 25 / 100, mHeight - 60, false)
            isInit = true  // set true once initialized

        }

        /** draw the canvas-color  */
        canvas.drawColor(Color.TRANSPARENT)

        mPaint!!.reset()
        mPaint!!.color = Color.rgb(229, 229, 229)
        mPaint!!.style = Paint.Style.FILL_AND_STROKE
        mPaint!!.strokeWidth = 6f
        mPaint!!.isAntiAlias = true
        mPaint!!.alpha = 80
        canvas.drawRoundRect(25f, 25f, mWidth.toFloat() - 35, mHeight.toFloat() - 35, 50f, 50f, mPaint!!)

        mPaint!!.reset()

        if (dragstarted) {
            canvas.drawBitmap(bitmap, 25f + a - mWidth * 25 / 100 / 2, 25f, mPaint)
        } else {
            canvas.drawBitmap(bitmap, 25f, 25f, mPaint)
        }


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> if (event.x > 25 && event.x < mWidth * 25 / 100) {
                dragstarted = true
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                a = event.x
                invalidate()
            }
            MotionEvent.ACTION_UP ->


                if (event.x < mWidth - mWidth * 25 / 100) {
                    dragstarted = false
                    invalidate()
                } else {
                    AlarmRecieverActivity.mMediaPlayer?.stop()
                    myactivity!!.finishAndRemoveTask()
                }
        }

        invalidate()
        return true

    }


}
