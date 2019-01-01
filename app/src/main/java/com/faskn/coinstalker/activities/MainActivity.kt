package com.faskn.coinstalker.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.faskn.coinstalker.R
import com.faskn.coinstalker.base.BaseActivity
import com.faskn.coinstalker.fragments.CoinsFragment
import com.faskn.coinstalker.fragments.ConverterFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    private val bottomNav by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }
    private var backPressCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val page: Int = item.itemId
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when (page) {
            R.id.menu_item_converter -> {
                transaction.replace(
                    R.id.container_fragment,
                    ConverterFragment(),
                    "ConverterFragment"
                ).commitNow()
            }
            R.id.menu_item_coins -> {
                transaction.replace(R.id.container_fragment, CoinsFragment(), "CoinsFragment")
                    .commitNow()
            }
        }
        return true
    }

}
