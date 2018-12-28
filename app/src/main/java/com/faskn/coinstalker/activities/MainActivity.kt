package com.faskn.coinstalker.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import com.faskn.coinstalker.R
import com.faskn.coinstalker.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_splash.*

class MainActivity : BaseActivity(),BottomNavigationView.OnNavigationItemReselectedListener {

    val bottomNav by lazy {  findViewById<BottomNavigationView>(R.id.bottom_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        object : CountDownTimer(4000, 1000) {

            override fun onFinish() {
                bottomNav.visibility = View.VISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {
                bottomNav.visibility = View.GONE
            }

        }.start()
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
