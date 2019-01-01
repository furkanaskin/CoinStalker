package com.faskn.coinstalker.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.faskn.coinstalker.R
import com.faskn.coinstalker.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    private val bottomNav by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupWithNavController(
            bottomNav,
            Navigation.findNavController(this, R.id.container_fragment)
        ) // here navView using Kotlin extension to avoid findviewbyid

    }


    override fun onBackPressed() {
        when (NavHostFragment.findNavController(container_fragment).currentDestination!!.id) {
            R.id.coinsFragment -> {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Gerçekten çıkış yapmak istiyor musunuz?")

                builder.setPositiveButton("Evet") { dialog, which ->
                    android.os.Process.killProcess(android.os.Process.myPid())
                }
                builder.setNegativeButton("Hayır") { dialog, which ->
                    Toast.makeText(this, "Pencere kapatıldı.", Toast.LENGTH_SHORT).show()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}
