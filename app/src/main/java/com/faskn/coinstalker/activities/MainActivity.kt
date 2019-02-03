package com.faskn.coinstalker.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        )

        setSupportActionBar(findViewById(R.id.toolbar))


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filtrele -> {
            Toast.makeText(this, "Filtrele", Toast.LENGTH_LONG).show()
            true
        }
        android.R.id.home -> {
            Toast.makeText(this, "Home action", Toast.LENGTH_LONG).show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
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
