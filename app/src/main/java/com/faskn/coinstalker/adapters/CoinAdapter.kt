package com.faskn.coinstalker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faskn.coinstalker.R
import com.faskn.coinstalker.model.Base
import com.faskn.coinstalker.model.Coin
import com.faskn.coinstalker.viewholders.CoinViewHolder

class CoinAdapter(
    private val coinList: ArrayList<Coin>,
    val base: Base,
    private val itemClickListener: (View, Int) -> Unit
) :
    RecyclerView.Adapter<CoinViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_coin_info, parent, false)

        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(coinList[position], base, itemClickListener)
    }
}