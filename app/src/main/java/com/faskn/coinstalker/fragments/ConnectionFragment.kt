package com.faskn.coinstalker.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.faskn.coinstalker.R
import com.faskn.coinstalker.viewmodels.CoinsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_connection.*


class ConnectionFragment : Fragment() {

    private val bottomNav by lazy { activity?.bottom_navigation }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this@ConnectionFragment)
            .get(CoinsViewModel::class.java) // Create vm

        btn_tryagain.setOnClickListener {

            viewModel.checkConnectionStatus()
            viewModel.connectionStatusLiveData.observe(this@ConnectionFragment, Observer { status ->
                if (status) {
                    navigate(R.id.action_connectionFragment_to_coinsFragment)
                    bottomNav?.visibility = View.VISIBLE
                    Log.v("qqq", "Connection successful.")
                } else {
                    bottomNav?.visibility = View.GONE
                    Log.v("qqq", "No internet connection.")
                }
            })
        }
    }

    private fun navigate(action: Int) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action)
        }
    }
}
