package com.faskn.coinstalker.viewholders

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faskn.coinstalker.R
import com.faskn.coinstalker.model.Base
import com.faskn.coinstalker.model.Coin
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import java.math.BigDecimal


class CoinViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    @SuppressLint("SetTextI18n")
    fun bind(coin: Coin, base: Base) {
        val txtShortCoinName = view.findViewById(R.id.tv_coinShortName) as TextView
        val txtFullCoinName = view.findViewById(R.id.tv_coinFullName) as TextView
        val txtPrice = view.findViewById(R.id.tv_coin_price) as TextView
        val txtChange = view.findViewById(R.id.tv_coin_change) as TextView
        val txtVolume = view.findViewById(R.id.tv_coin_volume) as TextView
        val txtMaxPrice = view.findViewById(R.id.tv_coin_maxPrice) as TextView
        val txtMarketCap = view.findViewById(R.id.tv_coin_marketcap) as TextView
        val ivCoinLogo = view.findViewById(R.id.iv_coinLogo) as ImageView
        val ivChanceArrow = view.findViewById(R.id.iv_chance_arrow) as ImageView



        txtShortCoinName.text = coin.symbol
        txtFullCoinName.text = coin.name
        txtPrice.text = priceBeautifier(coin.price, base.sign)
        txtChange.text = coin.change.toString()
        txtVolume.text = volumeBeautifier(coin.volume.toString(), base.sign)
        try {
            txtMarketCap.text = volumeBeautifier(coin.marketCap.toString(), base.sign)
        } catch (e: Exception) {
            txtMarketCap.text = "N/A"
            Log.v("bugCatcher", "${coin.name} Market Cap cannot found.")
        }
        txtMaxPrice.text = priceBeautifier(coin.allTimeHigh.price, base.sign)
        // Set green if + else set red (change)
        if (coin.change < 0) {
            txtChange.text = coin.change.toString()
            txtChange.setTextColor(view.context.resources.getColor(R.color.colorNegative))
            Glide.with(view.context).load(R.drawable.arrow_down).into(ivChanceArrow)

        } else {
            txtChange.text = "+${coin.change}"
            txtChange.setTextColor(view.context.resources.getColor(R.color.colorPosivite))
            Glide.with(view.context).load(R.drawable.arrow_up).into(ivChanceArrow)

        }

        val uri: Uri = Uri.parse(coin.iconUrl)
        GlideToVectorYou.justLoadImage(view.context as Activity, uri, ivCoinLogo)

    }

    private fun priceBeautifier(price: BigDecimal, sign: String): String {
        val afterDot = price.toString().substringAfterLast(".")
        val beforeDot = price.toString().substringBeforeLast(".")
        val after = afterDot.take(2)

        return "$sign$beforeDot.$after"
    }

    private fun volumeBeautifier(volume: String, sign: String): String {
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
}