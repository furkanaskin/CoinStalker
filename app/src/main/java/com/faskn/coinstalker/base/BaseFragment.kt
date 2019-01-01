package com.faskn.coinstalker.base

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

open class BaseFragment : Fragment() {
    fun navigate(action: Int) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action)
        }
    }
}