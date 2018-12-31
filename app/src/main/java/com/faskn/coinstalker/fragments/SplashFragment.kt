package com.faskn.coinstalker.fragments


import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.faskn.coinstalker.CoinsViewModel
import com.faskn.coinstalker.R
import kotlinx.android.synthetic.main.fragment_splash.*


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(CoinsViewModel::class.java) // Create vm

        val animationBlink = AnimationUtils.loadAnimation(this.context, R.anim.blink)
        val animationFadein = AnimationUtils.loadAnimation(this.context, R.anim.fade_in)
        val animationFadeout = AnimationUtils.loadAnimation(this.context, R.anim.fade_out)
        iv_coinstalker.visibility = View.GONE

        av_splash_animation.setAnimation("splashAnimation.json")
        av_splash_animation.speed = 3f
        av_splash_animation.playAnimation()

        Glide.with(view.context).load(R.drawable.coinranking).into(iv_coinRanking_logo)


        object : CountDownTimer(4000, 1000) {
            override fun onFinish() {
                av_splash_animation.visibility = View.GONE
                tv_loading.startAnimation(animationFadeout)
                tv_loading.visibility = View.GONE
                iv_coinstalker.visibility = View.VISIBLE
                iv_coinstalker.startAnimation(animationFadein)

                viewModel.checkConnectionStatus()
                viewModel.connectionStatusLiveData.observe(this@SplashFragment, Observer { status ->
                    if (status) {
                        Log.v("qqq", "Connection successful.")
                    } else {
                        navigate(R.id.action_splashFragment_to_connectionFragment)
                        Log.v("qqq", "No internet connection.")
                    }
                })

                object : CountDownTimer(3000, 1000) {
                    override fun onFinish() {
                        navigate(R.id.action_splashFragment_to_coinsFragment)
                    }

                    override fun onTick(millisUntilFinished: Long) {
                    }
                }.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                tv_loading.startAnimation(animationBlink)
                tv_loading.append(".")
            }

        }.start()
    }

    private fun navigate(action: Int) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action)
        }
    }


}
