package com.example.hobbyapp_utsanmp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.hobbyapp_utsanmp.R
import com.example.hobbyapp_utsanmp.databinding.ActivityMainBinding
import com.example.hobbyapp_utsanmp.model.Account
import com.example.hobbyapp_utsanmp.viewmodel.ProfileViewModel

class MainActivity : AppCompatActivity(), BottomNavigationVisibilityListener, OnDataPassListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController


    private lateinit var account: Account
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =
            (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController
        NavigationUI.setupActionBarWithNavController(this, navController)

        binding.bottomNav.setupWithNavController(navController)
    }

    override fun setBottomNavigationVisibility(isVisible: Boolean){
        if (isVisible) {
            binding.bottomNav.visibility = View.VISIBLE

        } else {
            binding.bottomNav.visibility = View.GONE
        }
    }

    override fun onDataPass(data: ArrayList<Account>) {
        Log.d("accountData", "${data[0]}")
        account = data[0]
    }

    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController, null) || super.onSupportNavigateUp()
        return navController.navigateUp()
    }
}