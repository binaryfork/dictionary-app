package com.bf.dict.ui.main

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bf.dict.R
import com.bf.dict.domain.repository.Translation
import com.bf.dict.ui.favorites.FavoritesFragment
import com.bf.dict.ui.translate.TranslateFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var tabsPagerAdapter: TabsPagerAdapter

    private val tabIcons = intArrayOf(
            R.drawable.selector_ic_language,
            R.drawable.selector_ic_star)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        tabsPagerAdapter = TabsPagerAdapter(fragmentManager)

        viewPager.adapter = tabsPagerAdapter
        viewPager.offscreenPageLimit = 2
        tabLayout.setupWithViewPager(viewPager)
        setupTabIcons()

        viewPager.addOnPageChangeListener(createPageChangeListener())
    }

    fun openMainFragment(translation: Translation) {
        val mainFragmentPosition = 0

        val fragment = tabsPagerAdapter.getFragment(mainFragmentPosition) as TranslateFragment?
                ?: return

        fragment.updateTranslation(translation)
        viewPager.setCurrentItem(mainFragmentPosition)
    }

    private fun createPageChangeListener(): ViewPager.OnPageChangeListener {
        return object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                hideKeyboard(this@MainActivity)
                if (position == 1) {
                    val fragment = tabsPagerAdapter.getFragment(position) as FavoritesFragment
                    fragment.onTabSelected()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        }
    }

    private fun setupTabIcons() {
        for (i in 0 until tabLayout.getTabCount()) {
            tabLayout.getTabAt(i)?.setIcon(tabIcons[i])
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity.currentFocus ?: View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
