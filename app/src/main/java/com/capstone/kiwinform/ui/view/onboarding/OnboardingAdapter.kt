package com.capstone.kiwinform.ui.view.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.capstone.kiwinform.R

class OnboardingAdapter(private var context: Context?) : PagerAdapter() {
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private val titles = intArrayOf(
        R.string.title_welcome,
        R.string.title_add_plans,
        R.string.title_reminder,
    )

    private val descriptions = intArrayOf(
        R.string.subtitle_onboarding_welcome,
        R.string.subtitle_onboarding_add_plans,
        R.string.subtitle_onboarding_reminder
    )

    private val images = intArrayOf(
        R.drawable.bear,
        R.drawable.bear,
        R.drawable.bear2
    )

    override fun getCount(): Int = titles.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v: View = layoutInflater.inflate(R.layout.fragment_getting_started, container, false)

        val image = v.findViewById<ImageView>(R.id.petImage)
        val title: TextView = v.findViewById(R.id.headline)
        val description: TextView = v.findViewById(R.id.description)

        image.setImageResource(images[position])
        title.setText(titles[position])
        description.setText(descriptions[position])

        container.addView(v)
        return v
    }

}