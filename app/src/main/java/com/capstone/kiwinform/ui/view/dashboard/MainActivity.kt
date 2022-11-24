package com.capstone.kiwinform.ui.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.capstone.kiwinform.R
import com.capstone.kiwinform.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.title_home,
            R.string.title_activities
        )

        private val TAB_ICONS = intArrayOf(
            R.drawable.ic_home,
            R.drawable.ic_activities
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
            tab.setIcon(TAB_ICONS[position])
            tab.setCustomView(R.layout.tab_layout_custom_view)
        }.attach()
    }
}