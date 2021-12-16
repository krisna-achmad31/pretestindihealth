package com.kris.indihealthpretest.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.kris.indihealthpretest.R
import dmax.dialog.SpotsDialog

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {
    lateinit var mProgressDialog: ProgressDialog
    lateinit var mProgressBar: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeProgressbar()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window?.statusBarColor = this.let { ContextCompat.getColor(it, R.color.colorPrimary) }
        } else {
            val window = this.window
            window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = this.let { ContextCompat.getColor(it, R.color.colorPrimaryDark) }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun initializeProgressbar() {

        mProgressBar = SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build()
        mProgressDialog = ProgressDialog(this)
    }

    fun showLoading() {
        if (!mProgressBar.isShowing) {
            mProgressBar.show()
            mProgressBar.setMessage(getString(R.string.lbl_loading))
            mProgressBar.setCancelable(false)
        }
//        if (!mProgressDialog.isShowing) {
//            mProgressDialog.show()
//            mProgressDialog.setMessage(msg)
//            mProgressDialog.setCancelable(false)
//        }
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun hideLoading() {
        if (mProgressBar.isShowing) {
            mProgressBar.dismiss()
        }
//        if (mProgressDialog.isShowing) {
//            mProgressDialog.dismiss()
//        }
    }

    fun pressBack(view: View) {
        onBackPressed()
    }

}