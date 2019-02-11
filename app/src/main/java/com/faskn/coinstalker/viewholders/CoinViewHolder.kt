package com.faskn.coinstalker.viewholders

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faskn.coinstalker.R
import com.faskn.coinstalker.model.Base
import com.faskn.coinstalker.model.Coin
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.mikhaellopez.circularimageview.CircularImageView
import java.math.BigDecimal


class CoinViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    @SuppressLint("SetTextI18n")
    fun bind(coin: Coin, base: Base, itemClickListener: (View, Int) -> Unit) {

        val txtShortCoinName: TextView = view.findViewById(R.id.tv_coinShortName)
        val txtFullCoinName: TextView = view.findViewById(R.id.tv_coinFullName)
        val txtPrice: TextView = view.findViewById(R.id.tv_coin_price)
        val txtChange: TextView = view.findViewById(R.id.tv_coin_change)
        val ivCoinLogo: CircularImageView = view.findViewById(R.id.iv_coinLogo)
        val ivChanceArrow: ImageView = view.findViewById(R.id.iv_chance_arrow)

        txtShortCoinName.text = coin.symbol
        txtFullCoinName.text = coin.name
        txtPrice.text = priceBeautifier(coin.price, base.sign)
        txtChange.text = coin.change.toString()

        // Set green if + else set red (change)
        if (coin.change < 0) {
            txtChange.text = coin.change.toString()
            txtChange.setTextColor(ContextCompat.getColor(view.context, R.color.colorNegative))
            Glide.with(view.context).load(R.drawable.arrow_down).into(ivChanceArrow)

        } else {
            txtChange.text = "+${coin.change}"
            txtChange.setTextColor(ContextCompat.getColor(view.context, R.color.colorPosivite))
            Glide.with(view.context).load(R.drawable.arrow_up).into(ivChanceArrow)

        }

        val uri: Uri = Uri.parse(coin.iconUrl)
        GlideToVectorYou.justLoadImage(view.context as Activity, uri, ivCoinLogo)

        try {
            val color = Color.parseColor(coin.color)
            ivCoinLogo.setBorderColor(color)
        } catch (e: java.lang.Exception) {
            Log.e("BorderColor", "Cannot set border color. Exception : $e")
        }

        try {
            val color = Color.parseColor(coin.color)
            txtFullCoinName.setTextColor(color)
        } catch (e: java.lang.Exception) {
            Log.e("BorderColor", "Cannot set text color. Exception : $e")
        }

        itemView.setOnClickListener { itemClickListener(view, coin.id) }
    }

    private fun priceBeautifier(price: BigDecimal, sign: String): String {
        val afterDot = price.toString().substringAfterLast(".")
        val beforeDot = price.toString().substringBeforeLast(".")
        val after = afterDot.take(2)

        return "$beforeDot.$after$sign"
    }

}