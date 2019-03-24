package com.faskn.coinstalker.ui.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.faskn.coinstalker.R
import com.faskn.coinstalker.base.BaseFragment
import com.faskn.coinstalker.utils.MyFormatter
import com.github.aakira.expandablelayout.ExpandableRelativeLayout
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_coin_info.*
import kotlin.properties.Delegates


class CoinInfoFragment : BaseFragment() {

    private var expandableLayout: ExpandableRelativeLayout by Delegates.notNull()
    private lateinit var commentCoinSymbol: String // Define a string for CommentsFragment bundle.
    private lateinit var commentCoinSign: String
    private val animationFadein by lazy {
        AnimationUtils.loadAnimation(
            this.context,
            R.anim.fade_in
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_coin_info, container, false)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getCoinInfo(getCoinID(), getBase())

        tv_selectedBar.visibility = View.GONE //Hide selected bar text

        viewModel.coinInfoLiveData.observe(this@CoinInfoFragment, Observer { Data ->

            // Data filling stuffs starts here..
            commentCoinSymbol = Data.coin.symbol
            commentCoinSign = Data.base.sign

            tv_info_coinName.text = Data.coin.name
            tv_info_coin_rank.text = "#${Data.coin.rank}"
            if (Data.coin.change > 0) {
                tv_info_coin_change.text = "+${Data.coin.change}"
                tv_info_coin_change.setTextColor(
                    getColor(
                        this.context!!,
                        R.color.colorPosivite
                    )
                )
                Glide.with(view.context).load(R.drawable.arrow_up).into(iv_info_chance_arrow)
            } else {
                tv_info_coin_change.text = Data.coin.change.toString()
                tv_info_coin_change.setTextColor(
                    getColor(
                        this.context!!,
                        R.color.colorNegative
                    )
                )
                Glide.with(view.context).load(R.drawable.arrow_down).into(iv_info_chance_arrow)


            }
            tv_info_coin_description.text = Data.coin.description
            tv_info_rank_text.text = getString(R.string.siralama)
            tv_infoDetail_text_allTimeHigh.text = getString(R.string.en_yks)
            tv_infoDetail_text_currentValue.text = getString(R.string.suan)
            tv_infoDetail_text_marketCap.text = getString(R.string.piyasa_degeri)
            tv_infoDetail_text_volume.text = getString(R.string.hacim)
            tv_infoDetail_text_circulatingSupply.text = getString(R.string.piyasadakiPara)
            tv_infoDetail_text_totalSupply.text = getString(R.string.toplamPara)

            tv_infoDetail_allTimeHigh.text =
                    priceBeautifier(Data.coin.allTimeHigh.price, commentCoinSign)
            tv_infoDetail_volume.text =
                    volumeBeautifier(Data.coin.volume.toString(), commentCoinSign)
            tv_infoDetail_marketCap.text =
                    volumeBeautifier(Data.coin.marketCap.toString(), commentCoinSign)
            tv_infoDetail_currentValue.text = priceBeautifier(Data.coin.price, commentCoinSign)

            tv_infoDetail_circulatingSupply.text = "${Data.coin.circulatingSupply} Adet"
            tv_infoDetail_totalSupply.text = "${Data.coin.totalSupply} Adet"

            val uri: Uri = Uri.parse(Data.coin.iconUrl)
            Glide.with(view.context as Activity).load(uri).into(iv_info_coinLogo)
            try {
                val color = Color.parseColor(Data.coin.color)
                iv_info_coinLogo.setBorderColor(color)
            } catch (e: java.lang.Exception) {
                Log.v("colorSetter", "Used default color.")
            }

            // Data filling stuffs ends here..
        })



        viewModel.getCoinHistory(getCoinID(), getBase())  // Get coinHistory for barChart
        val barGroup = ArrayList<BarEntry>()
        val timestampArray = ArrayList<Float>()
        viewModel.coinHistoryLiveData.observe(this@CoinInfoFragment, Observer { Data ->

            // Data filling stuffs(BarChart) starts here..
            Data.history.forEachIndexed { index, element ->
                val formattedTimeStamp = (element.timestamp / 10000).toFloat()
                timestampArray.add(formattedTimeStamp)
                barGroup.add(BarEntry(index.toFloat(), element.price.toFloat()))
            }
            // creating data set for Bar Group
            val barDataSet = BarDataSet(barGroup, "24 Saatlik değerler")

            barDataSet.color = ContextCompat.getColor(
                this.context!!, R.color.colorPrettyOrange
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

                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    val date = MyFormatter(timestampArray).getFormattedValue(
                        e!!.x,
                        axis = null
                    )

                    tv_selectedBar.text = "${e.y}$commentCoinSign  $date"
                    tv_selectedBar.visibility = View.VISIBLE
                    tv_selectedBar.startAnimation(animationFadein)

                }
            })

            // Data filling stuffs(BarChart) ends here..
        })

        expandableLayout = view.findViewById(R.id.expandableLayout) as ExpandableRelativeLayout
        card_expandable?.setOnClickListener {
            if (expandableLayout.isExpanded) {
                val willBeDeleted = fragmentManager?.findFragmentByTag("commentFragment")
                val transaction: FragmentTransaction = fragmentManager?.beginTransaction()!!
                transaction.remove(willBeDeleted!!).commitNow()
                expandableLayout.collapse()
                iv_expandableIcon.setImageResource(R.drawable.arrow_down)

            } else {
                expandableLayout.expand()
                expandableLayout.move(500)
                val commentsFragment = CommentsFragment.newInstance(commentCoinSymbol)
                val transaction: FragmentTransaction = fragmentManager?.beginTransaction()!!
                transaction.add(R.id.frame_comments, commentsFragment, "commentFragment")
                    .commit()
                iv_expandableIcon.setImageResource(R.drawable.arrow_up)
            }
        }
    }

    fun getCoinID(): Int {
        return CoinInfoFragmentArgs.fromBundle(this.arguments!!).coinID
    }
}

