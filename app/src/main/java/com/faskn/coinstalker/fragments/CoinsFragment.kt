package com.faskn.coinstalker.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faskn.coinstalker.R
import com.faskn.coinstalker.adapters.CoinAdapter
import com.faskn.coinstalker.model.Coin
import com.faskn.coinstalker.network.RetrofitFactory
import kotlinx.android.synthetic.main.fragment_coins.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoinsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coins, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pb_coins.visibility = View.VISIBLE

        val coinsRecyclerView = view.findViewById<RecyclerView>(R.id.container_coins)
        coinsRecyclerView.layoutManager =
                LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)

        GlobalScope.launch(Dispatchers.Main) {
            val service = RetrofitFactory.makeRetrofitService()
            val coinsData = service.getCoins().await()

            if (coinsData.isSuccessful) {
                pb_coins.visibility = View.GONE
                val coins = coinsData.body()?.data!!.coins
                val base = coinsData.body()?.data!!.base
                coinsRecyclerView.adapter = CoinAdapter(coins as ArrayList<Coin>, base)
            }
        }
    }


}
