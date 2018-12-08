package com.nusretozates.wake_up.CustomViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*

class CustomAnalogClock : View {

    /**
     * height, width of the clock's view
     */
    private var mHeight: Int = 0
    private var mWidth = 0

    /**
     * numeric numbers to denote the hours
     */
    private val mClockHours = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    /**
     * spacing and padding of the clock-hands around the clock round
     */
    private var mPadding = 0
    private val mNumeralSpacing = 0

    /**
     * truncation of the heights of the clock-hands,
     * hour clock-hand will be smaller comparetively to others
     */
    private var mHandTruncation: Int = 0
    private var mHourHandTruncation = 0

    /**
     * others attributes to calculate the locations of hour-points
     */
    private var mRadius = 0
    private var mPaint: Paint? = null
    private val mRect = Rect()
    private var isInit: Boolean = false  // it will be true once the clock will be initialized.

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        // TODO: Implementation of the analog-clock UI in runtime goes here.
        /** initialize necessary values  */
        if (!isInit) {
            mPaint = Paint()
            mHeight = height
            mWidth = width
            mPadding = mNumeralSpacing + 50  // spacing from the circle border
            val minAttr = Math.min(mHeight, mWidth)
            mRadius = minAttr / 2 - mPadding

            // for maintaining different heights among the clock-hands
            mHandTruncation = minAttr / 20
            mHourHandTruncation = minAttr / 17

            isInit = true  // set true once initialized
        }

        /** draw the canvas-color  */
        canvas.drawColor(Color.rgb(229, 229, 229))


        /** circle border  */
        mPaint!!.reset()
        mPaint!!.color = Color.rgb(242, 242, 242)
        mPaint!!.style = Paint.Style.FILL_AND_STROKE
        mPaint!!.strokeWidth = 4f
        mPaint!!.isAntiAlias = true
        canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), (mRadius + mPadding - 10).toFloat(), mPaint!!)

        /** clock-center  */
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.color = Color.rgb(51, 51, 51)
        canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), 12f, mPaint!!)  // the 03 clock hands will be rotated from this center point.

        mPaint!!.reset()
        val fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics).toInt()
        mPaint!!.textSize = fontSize.toFloat()  // set font size (optional)


        for (hour in mClockHours) {
            val tmp = hour.toString()
            mPaint!!.getTextBounds(tmp, 0, tmp.length, mRect)  // for circle-wise bounding
            mPaint!!.color = Color.rgb(79, 79, 79)
            // find the circle-wise (x, y) position as mathematical rule
            val angle = Math.PI / 6 * (hour - 3)
            val x = (mWidth / 2 + Math.cos(angle) * mRadius - mRect.width() / 2).toInt()
            val y = ((mHeight / 2).toDouble() + Math.sin(angle) * mRadius + (mRect.height() / 2).toDouble()).toInt()

            canvas.drawText(hour.toString(), x.toFloat(), y.toFloat(), mPaint!!)  // you can draw dots to denote hours as alternative
            /** draw clock hands to represent the every single time  */

            val calendar = Calendar.getInstance()
            var houra = calendar.get(Calendar.HOUR_OF_DAY).toFloat()
            houra = if (houra > 12) houra - 12 else houra

            drawHandLine(canvas, ((houra + calendar.get(Calendar.MINUTE) / 60) * 5f).toDouble(), true, false) // draw hours
            drawHandLine(canvas, calendar.get(Calendar.MINUTE).toDouble(), false, false) // draw minutes
            drawHandLine(canvas, calendar.get(Calendar.SECOND).toDouble(), false, true) // draw seconds

            /** invalidate the appearance for next representation of time   */
            postInvalidateDelayed(500)
            invalidate()

        }
    }

    private fun drawHandLine(canvas: Canvas, moment: Double, isHour: Boolean, isSecond: Boolean) {
        val angle = Math.PI * moment / 30 - Math.PI / 2
        val handRadius = if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation
        mPaint!!.color = Color.BLACK
        mPaint!!.strokeWidth = 7.0f
        canvas.drawLine((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), (mWidth / 2 + Math.cos(angle) * handRadius).toFloat(), (mHeight / 2 + Math.sin(angle) * handRadius).toFloat(), mPaint!!)
    }
}