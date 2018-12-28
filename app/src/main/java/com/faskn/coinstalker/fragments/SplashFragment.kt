package com.faskn.coinstalker.fragments


import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.faskn.coinstalker.R
import kotlinx.android.synthetic.main.fragment_splash.*


class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationBlink = AnimationUtils.loadAnimation(this.context, R.anim.blink)

        av_splash_animation.setAnimation("splashAnimation.json")
        av_splash_animation.speed = 4f
        av_splash_animation.playAnimation()

        object : CountDownTimer(1000, 1000) {
            override fun onFinish() {
                navigate(R.id.action_splashFragment_to_coinsFragment)
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
