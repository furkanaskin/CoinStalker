package com.faskn.coinstalker.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.faskn.coinstalker.utils.SharedPrefKey
import com.faskn.coinstalker.viewmodels.CoinsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

open class BaseFragment : Fragment() {

    private val sharedPref by lazy {
        activity?.getSharedPreferences(
            SharedPrefKey.PrivateSharedPref.toString(),
            Context.MODE_PRIVATE
        )
    }

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

    fun getBase(): String? {
        return sharedPref?.getString(SharedPrefKey.Base.toString(), "TRY")
    }

    fun getSort(): String? {
        return sharedPref?.getString(SharedPrefKey.SortFilter.toString(), "coinranking")
    }

    fun getTimePeriod(): String? {
        return sharedPref?.getString(SharedPrefKey.TimePeriod.toString(), "24h")
    }

}