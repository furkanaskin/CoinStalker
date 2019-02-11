package com.faskn.coinstalker.utils

import android.annotation.SuppressLint
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class MyFormatter(val data: ArrayList<Float>) : IAxisValueFormatter {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return unixTimeStampFormatter(data[value.toInt()].toLong())
    }

    @SuppressLint("SimpleDateFormat")
    fun unixTimeStampFormatter(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = java.util.Date(timestamp * 10000)
        sdf.format(date)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)
        val hour = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"

        return "$month $day $hour"
    }
}