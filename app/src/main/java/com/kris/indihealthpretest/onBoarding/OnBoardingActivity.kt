package com.kris.indihealthpretest.onBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.kris.indihealthpretest.R
import com.kris.indihealthpretest.base.BaseActivity
import com.kris.indihealthpretest.databinding.ActivityOnBoardingBinding
import com.kris.indihealthpretest.extension.makeInVisible
import com.kris.indihealthpretest.extension.makeVisible
import com.kris.indihealthpretest.login.LoginActivity
import com.kris.indihealthpretest.models.onBoarding.OnBoardingList

class OnBoardingActivity : BaseActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    val dots = arrayOfNulls<TextView>(3)
    var currentpage: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        picture()
        layoutSetup()
    }

    private fun picture() {
        val listImage = ArrayList<OnBoardingList>()
        listImage.add(OnBoardingList(getString(R.string.lbl_title_on_boarding),getString(R.string.lbl_description_on_boarding), R.drawable.on_boarding1))
        listImage.add(OnBoardingList(getString(R.string.lbl_title_on_boarding),getString(R.string.lbl_description_on_boarding), R.drawable.on_boarding2))
        listImage.add(OnBoardingList(getString(R.string.lbl_title_on_boarding),getString(R.string.lbl_description_on_boarding), R.drawable.on_boarding3))
        binding.vpOnboard.adapter =ImageSliderAdapter(this, listImage) { position ->
            if (position == 2) {
                toLogin()
            }

        }

        binding.ciOnboard.setViewPager(binding.vpOnboard)
        val density = resources.displayMetrics.density
        val frameOnBoarding = layoutInflater.inflate(R.layout.layout_on_boarding, binding.root, false)

        binding.ciOnboard.radius = 5 * density


        binding.vpOnboard.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }


            override fun onPageSelected(position: Int) {
                currentpage = position
                if (position == 2) {
//                    binding.btnOnboard.makeVisible()
                    binding.btnOnBoarding.setText("Get Started")
                    binding.btnOnBoarding.setRippleColorResource(R.color.colorHoverButton)
                }else{
                    binding.btnOnBoarding.setText("Next")
                    binding.btnOnBoarding.setRippleColorResource(R.color.colorAccent)

                }
                if (position == 0){
                    binding.tvPrevious.makeInVisible()
                }else {
                    binding.tvPrevious.makeVisible()

                }

            }

        })

        binding.tvPrevious.setOnClickListener{
            if (binding.vpOnboard.currentItem +1 <= dots.size){
                binding.vpOnboard.currentItem -=1
            }
        }
    }

    private fun layoutSetup() {
        binding.btnOnBoarding.setOnClickListener {
            if (binding.btnOnBoarding.text == "Get Started" ){
                toLogin()
                binding.btnOnBoarding.rippleColor to R.color.colorHoverButton
            }else{
                if (binding.vpOnboard.currentItem +1 <= dots.size){
                    binding.vpOnboard.currentItem +=1
                }
            }
        }
    }

    private fun toLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}