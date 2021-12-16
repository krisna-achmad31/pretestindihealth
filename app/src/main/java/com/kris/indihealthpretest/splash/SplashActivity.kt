package com.kris.indihealthpretest.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.kris.indihealthpretest.base.BaseActivity
import com.kris.indihealthpretest.databinding.ActivitySplashBinding
import com.kris.indihealthpretest.onBoarding.OnBoardingActivity

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    private var mDelayHandler: Handler? = null
    private val delay: Long = 3000
    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDelayHandler = Handler(Looper.getMainLooper())
        mDelayHandler!!.postDelayed(mRunnable, delay)
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}