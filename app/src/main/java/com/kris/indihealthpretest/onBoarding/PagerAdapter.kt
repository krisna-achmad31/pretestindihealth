package com.kris.indihealthpretest.onBoarding

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.kris.indihealthpretest.R
import com.kris.indihealthpretest.extension.inflate
import com.kris.indihealthpretest.models.onBoarding.OnBoardingList

class ImageSliderAdapter(private  var context: Context, var data: List<OnBoardingList>, private val onClick:(position: Int) -> Unit): PagerAdapter(){

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = container.inflate(R.layout.layout_on_boarding, false)
        val image: ImageView = view.findViewById(R.id.ivAdapterOnBoardImage)
        val title: TextView = view.findViewById(R.id.tvOnBoardingTitle)
        val subtitle: TextView = view.findViewById(R.id.tvOnBoardingSubtitle)
//        val button: MaterialButton = view.findViewById(R.id.btnOnBoarding)

        Glide.with(context).load(data[position].image).apply { }.into(image)
        title.text = data[position].title
        subtitle.text = data[position].subtitle

//        if (position == 2)
//            button.setText("Get Started")
//        else button.makeVisible()
//
//        button.setOnClickListener {
//            onClick(position)
//        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }


    override fun getCount(): Int {
        return data.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


}