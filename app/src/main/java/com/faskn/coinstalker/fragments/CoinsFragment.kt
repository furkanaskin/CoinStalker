package com.faskn.coinstalker.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faskn.coinstalker.R
import com.faskn.coinstalker.adapters.CoinAdapter
import com.faskn.coinstalker.base.BaseFragment
import com.faskn.coinstalker.model.Coin
import com.faskn.coinstalker.viewmodels.CoinsViewModel
import kotlinx.android.synthetic.main.fragment_coins.*


class CoinsFragment : BaseFragment() {

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

        val viewModel = ViewModelProviders.of(this).get(CoinsViewModel::class.java) // Create vm
        val coinsRecyclerView = view.findViewById<RecyclerView>(R.id.container_coins)
        coinsRecyclerView.layoutManager =
                LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)


        viewModel.getCoins()
        viewModel.coinsLiveData.observe(this@CoinsFragment, Observer { Data ->
            val adapter = CoinAdapter(Data.coins as ArrayList<Coin>, Data.base)
            coinsRecyclerView.swapAdapter(adapter, false)
            pb_coins.visibility = View.GONE
        })
    }
}
