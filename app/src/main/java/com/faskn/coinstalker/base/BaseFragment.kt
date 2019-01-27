package com.faskn.coinstalker.base

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

open class BaseFragment : Fragment() {
    fun navigate(action: Int) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action)
        }
    }

    fun priceBeautifier(price: BigDecimal, sign: String): String {
        val afterDot = price.toString().substringAfterLast(".")
        val beforeDot = price.toString().substringBeforeLast(".")
        val after = afterDot.take(2)

        return "$sign$beforeDot.$after"
    }

    fun volumeBeautifier(volume: String, sign: String): String {
        return when (volume.length) {
            4 -> sign + volume.substring(0, 1) + "." + volume.substring(1, 3) + "Bin"
            5 -> sign + volume.substring(0, 2) + "." + volume.substring(2, 4) + "Bin"
            6 -> sign + volume.substring(0, 3) + "." + volume.substring(3, 5) + "Bin"
            7 -> sign + volume.substring(0, 1) + "." + volume.substring(1, 3) + "Mn"
            8 -> sign + volume.substring(0, 2) + "." + volume.substring(2, 4) + "Mn"
            9 -> sign + volume.substring(0, 3) + "." + volume.substring(3, 5) + "Mn"
            10 -> sign + volume.substring(0, 1) + "." + volume.substring(1, 3) + "Mr"
            11 -> sign + volume.substring(0, 2) + "." + volume.substring(2, 4) + "Mr"
            12 -> sign + volume.substring(0, 3) + "." + volume.substring(3, 5) + "Mr"
            else -> sign + volume
        }
    }

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
}