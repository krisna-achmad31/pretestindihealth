package com.kris.indihealthpretest.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kris.indihealthpretest.R
import com.kris.indihealthpretest.databinding.ActivityMainBinding
import com.kris.indihealthpretest.extension.toast
import com.kris.indihealthpretest.main.home.HomeFragment
import com.kris.indihealthpretest.main.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val home = HomeFragment()
    private val other = ProfileFragment()

    private var doubleBackToExitPressedOnce: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, home)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val transaction = supportFragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.navigation_home -> {
                transaction.replace(R.id.container, home)
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_other -> {
                transaction.replace(R.id.container, other)
                transaction.addToBackStack(null)
                transaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onBackPressed() {
        val delay: Long = 1000

        val mRunnable = Runnable {
            if (!isFinishing) {
                doubleBackToExitPressedOnce = false
            }
        }

        val fragments: Int = supportFragmentManager.backStackEntryCount
        if (fragments == 1) {
            if (doubleBackToExitPressedOnce) {
                val startMain = Intent(Intent.ACTION_MAIN)
                startMain.addCategory(Intent.CATEGORY_HOME)
                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(startMain)
                finish()
            }

            this.doubleBackToExitPressedOnce = true
            toast(getString(R.string.lbl_press_back_again_to_exit))
            Handler().postDelayed(mRunnable, delay)
        } else {
            if (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
            } else super.onBackPressed()
        }
    }
}