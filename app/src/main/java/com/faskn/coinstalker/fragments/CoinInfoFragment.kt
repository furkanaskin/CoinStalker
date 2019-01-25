package com.faskn.coinstalker.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.faskn.coinstalker.R
import kotlinx.android.synthetic.main.fragment_coin_info.*

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
        tv_info.text = getCoinID().toString()
    }

    private fun getCoinID(): Int {
        return CoinInfoFragmentArgs.fromBundle(this.arguments!!).coinID
    }
}
