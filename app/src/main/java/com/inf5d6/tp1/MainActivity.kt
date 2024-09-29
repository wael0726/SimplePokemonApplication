package com.inf5d6.tp1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    /**
     *  #    #  ######          #####     ##     ####
     *  ##   #  #               #    #   #  #   #
     *  # #  #  #####           #    #  #    #   ####
     *  #  # #  #               #####   ######       #
     *  #   ##  #               #       #    #  #    #
     *  #    #  ######          #       #    #   ####
     *
     *  #    #   ####   #####      #    ######     #    ######  #####
     *  ##  ##  #    #  #    #     #    #          #    #       #    #
     *  # ## #  #    #  #    #     #    #####      #    #####   #    #
     *  #    #  #    #  #    #     #    #          #    #       #####
     *  #    #  #    #  #    #     #    #          #    #       #   #
     *  #    #   ####   #####      #    #          #    ######  #    #
     */

    companion object {
        const val SRVURL = "https://pokemonsapi.herokuapp.com"
        var TOKEN = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_favorites)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        if (navController.navigateUp()) {
            return true
        }
        return super.onSupportNavigateUp()
    }

    /**
     *  #    #  ######          #####     ##     ####
     *  ##   #  #               #    #   #  #   #
     *  # #  #  #####           #    #  #    #   ####
     *  #  # #  #               #####   ######       #
     *  #   ##  #               #       #    #  #    #
     *  #    #  ######          #       #    #   ####
     *
     *  #    #   ####   #####      #    ######     #    ######  #####
     *  ##  ##  #    #  #    #     #    #          #    #       #    #
     *  # ## #  #    #  #    #     #    #####      #    #####   #    #
     *  #    #  #    #  #    #     #    #          #    #       #####
     *  #    #  #    #  #    #     #    #          #    #       #   #
     *  #    #   ####   #####      #    #          #    ######  #    #
     */
}