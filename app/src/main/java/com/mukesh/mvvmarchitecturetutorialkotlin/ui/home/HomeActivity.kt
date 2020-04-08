package com.mukesh.mvvmarchitecturetutorialkotlin.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.mukesh.mvvmarchitecturetutorialkotlin.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    /**
     * Steps to setup Navigation Drawer with NavigationGraph
     * 1> Get the NavigationController
     * 2> Setup NavigationUI with navigation view and NavController
     * (We need to setup action bar with NavController so that title will change automatically)
     * 3> SetupActionBarWithNavController with DrawerLayout
     * 4> Setup the Back button using override onSupportNavigateUp()
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(navigation_view, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment),
            drawer_layout
        )
    }
}
