package com.faskn.coinstalker.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.faskn.coinstalker.R
import com.faskn.coinstalker.viewmodels.CoinsViewModel

class CoinInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coin_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(CoinsViewModel::class.java) // Create vm
        viewModel.getCoinInfo(getCoinID())
        viewModel.coinInfoLiveData.observe(this@CoinInfoFragment, Observer { Data ->

        })

        viewModel.getCoinHistory(getCoinID())
        viewModel.coinHistoryLiveData.observe(this@CoinInfoFragment, Observer {

        })

    }

    private fun getCoinID(): Int {
        return CoinInfoFragmentArgs.fromBundle(this.arguments!!).coinID
    }

}
