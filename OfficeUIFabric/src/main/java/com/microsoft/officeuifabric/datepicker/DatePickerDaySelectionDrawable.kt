/**
 * Copyright © 2018 Microsoft Corporation. All rights reserved.
 * */

package com.microsoft.officeuifabric.datepicker

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat

import com.microsoft.officeuifabric.R

/**
 * [DatePickerDaySelectionDrawable] is a drawable added to a [DatePickerDayView] displaying selected state
 */
class DatePickerDaySelectionDrawable : Drawable {
    enum class Mode {
        SINGLE, START, END, MIDDLE
    }

    private val context: Context
    private val mode: Mode
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val circleSize: Int

    constructor(context: Context, mode: Mode) {
        this.context = context
        this.mode = mode
        paint.color = ContextCompat.getColor(context, R.color.uifabric_primary)
        circleSize = context.resources.getDimensionPixelSize(R.dimen.uifabric_date_picker_view_day_selection_size)
    }

    override fun draw(canvas: Canvas) {
        val width = intrinsicWidth.toFloat()
        val height = intrinsicHeight.toFloat()
        val centerX = width / 2
        val centerY = height / 2
        val radius = circleSize / 2

        when (mode) {
            Mode.SINGLE -> canvas.drawCircle(centerX, centerY, radius.toFloat(), paint)
            Mode.START -> {
                canvas.drawCircle(centerX, centerY, radius.toFloat(), paint)
                canvas.drawRect(centerX, (centerY - radius), width, (centerY + radius), paint)
            }
            Mode.MIDDLE -> canvas.drawRect(0f, (centerY - radius), width, (centerY + radius), paint)
            Mode.END -> {
                canvas.drawCircle(centerX, centerY, radius.toFloat(), paint)
                canvas.drawRect(0f, (centerY - radius), centerX, (centerY + radius), paint)
            }
        }
    }

    override fun getIntrinsicHeight() = bounds.height()

    override fun getIntrinsicWidth() = bounds.width()

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity() = PixelFormat.TRANSLUCENT
}
