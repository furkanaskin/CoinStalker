package com.faskn.coinstalker.fragments


import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.faskn.coinstalker.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_splash.*


class SplashFragment : BaseFragment() {

    private val bottomNav by lazy { activity?.bottom_navigation }
    private val actionBar by lazy { activity?.toolbar }
    private var countDownTimer: CountDownTimer? = null
    private val animationFadeIn by lazy {
        AnimationUtils.loadAnimation(this.context!!, com.faskn.coinstalker.R.anim.fade_in)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.faskn.coinstalker.R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this.context!!).load(com.faskn.coinstalker.R.drawable.coinranking)
            .into(iv_coinRanking_logo)

        iv_coinstalker.visibility = View.VISIBLE
        iv_coinstalker.startAnimation(animationFadeIn)

        countDownTimer = object : CountDownTimer(4000, 1000) {
            override fun onFinish() {
                viewModel.checkConnectionStatus()
                viewModel.connectionStatusLiveData.observe(this@SplashFragment, Observer { status ->
                    if (status) {
                        navigate(com.faskn.coinstalker.R.id.action_splashFragment_to_coinsFragment)
                        bottomNav?.visibility = View.VISIBLE
                        actionBar?.visibility = View.VISIBLE
                        Log.v("Connection Info", "Connection successful.")
                    } else {
                        navigate(com.faskn.coinstalker.R.id.action_splashFragment_to_connectionFragment)
                        bottomNav?.visibility = View.GONE
                        actionBar?.visibility = View.GONE
                        Log.v("Connection Info", "No internet connection.")
                    }
                })
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
