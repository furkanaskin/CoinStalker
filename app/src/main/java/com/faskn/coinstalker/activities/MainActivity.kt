package com.faskn.coinstalker.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.faskn.coinstalker.R
import com.faskn.coinstalker.base.BaseActivity
import com.faskn.coinstalker.fragments.CoinsFragment
import com.faskn.coinstalker.fragments.ConverterFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    private val bottomNav by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }

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

        bottomNav.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val page: Int = item.itemId
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()!!
        when (page) {
            R.id.menu_item_converter -> {
                transaction.replace(R.id.container_fragment, ConverterFragment()).commitNow()
            }
            R.id.menu_item_coins -> {
                transaction.replace(R.id.container_fragment, CoinsFragment()).commitNow()
            }
        }

        return true
    }

}
