package com.faskn.coinstalker.fragments


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.faskn.coinstalker.R
import com.faskn.coinstalker.base.BaseFragment
import com.faskn.coinstalker.viewmodels.CoinsViewModel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_coin_info.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CoinInfoFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.faskn.coinstalker.R.layout.fragment_coin_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationFadein =
            AnimationUtils.loadAnimation(this.context, com.faskn.coinstalker.R.anim.fade_in)

        tv_selectedBar.visibility = View.GONE //Hide selected bar text

        val viewModel = ViewModelProviders.of(this).get(CoinsViewModel::class.java) // Create vm
        viewModel.getCoinInfo(getCoinID())
        viewModel.coinInfoLiveData.observe(this@CoinInfoFragment, Observer { Data ->

        })

        viewModel.getCoinHistory(getCoinID())
        val barGroup = ArrayList<BarEntry>()
        val timestampArray = ArrayList<Float>()
        viewModel.coinHistoryLiveData.observe(this@CoinInfoFragment, Observer { Data ->
            Data.history.forEachIndexed { index, element ->
                val formattedTimeStamp = (element.timestamp / 10000).toFloat()
                timestampArray.add(formattedTimeStamp)
                barGroup.add(BarEntry(index.toFloat(), element.price.toFloat()))
            }
            // creating data set for Bar Group
            val barDataSet = BarDataSet(barGroup, "24 Saatlik değerler")

            barDataSet.color = ContextCompat.getColor(
                this.context!!, R.color.colorPrimaryDark
            )

            val data = BarData(barDataSet)
            val formatter = MyFormatter(timestampArray)
            barChart.data = data
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.xAxis.labelCount = 2
            barChart.xAxis.textSize = 1f
            barChart.xAxis.axisMinimum = 0f
            barChart.xAxis.axisMaximum = (barGroup.size).toFloat()
            barChart.xAxis.valueFormatter = formatter
            barChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
            barChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
            barChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
            barChart.description.isEnabled = false
            barChart.animateY(500)
            barChart.legend.isEnabled = false
            barChart.setNoDataTextColor(Color.parseColor("#EF6C00"))
            barChart.setBorderColor(Color.parseColor("#EF6C00"))
            barChart.axisLeft.textColor = Color.parseColor("#EF6C00")
            barChart.axisRight.textColor = Color.parseColor("#EF6C00")
            barChart.xAxis.textColor = Color.parseColor("#FFFFFF")
            barChart.data.setValueTextColor(Color.parseColor("#EF6C00"))
            barChart.axisLeft.isEnabled = false // Hide left axis
            barChart.setVisibleXRangeMaximum((barGroup.size / 7).toFloat())
            barChart.moveViewToX((barGroup.size).toFloat())
            barChart.setPinchZoom(true) // enable zoom
            barChart.data.setDrawValues(false) // hide values

            barChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onNothingSelected() {
                }

                @SuppressLint("SetTextI18n")
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    val date = MyFormatter(timestampArray).getFormattedValue(
                        e!!.x,
                        axis = null
                    )  // I just want to take formatted dateee !!!!

                    tv_selectedBar.text = "${e.y}₺  $date"
                    tv_selectedBar.visibility = View.VISIBLE
                    tv_selectedBar.startAnimation(animationFadein)

                }
            })
        })
    }

    private fun getCoinID(): Int {
        return CoinInfoFragmentArgs.fromBundle(this.arguments!!).coinID
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
