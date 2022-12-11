package com.capstone.kiwinform.ui.view.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.capstone.kiwinform.R
import com.capstone.kiwinform.ui.view.dashboard.MainActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var next: CardView
    private lateinit var dotsLayout: LinearLayout
    private lateinit var saveState: SaveState
    private lateinit var skip: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        viewPager = findViewById(R.id.viewPager)
        next = findViewById(R.id.nextCard)
        dotsLayout = findViewById(R.id.dotsLayout)
        saveState = SaveState(this, "ob")
        skip = findViewById(R.id.skip_button)

        if (saveState.getState() === 1) {
            startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
            finish()
        }

        val adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter
        next.setOnClickListener { viewPager.setCurrentItem(1, true) }
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                next.setOnClickListener {
                    if (position < 2) {
                        viewPager.setCurrentItem(position + 1, true)
                    } else {
                        saveState.setState(1)
                        startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        skip.setOnClickListener {
            saveState.setState(1)
            startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
            finish()
        }
    }
}