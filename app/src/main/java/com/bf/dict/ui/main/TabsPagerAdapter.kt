package com.bf.dict.ui.main

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bf.dict.ui.favorites.FavoritesFragment
import com.bf.dict.ui.translate.TranslateFragment

class TabsPagerAdapter internal constructor(private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 2
    }

    private val fragmentTags: SparseArray<String> = SparseArray(2)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val frag = super.instantiateItem(container, position)
        if (frag is Fragment) {
            val tag = frag.tag
            fragmentTags.put(position, tag)
        }
        return frag
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return TranslateFragment()
            1 -> return FavoritesFragment()
        }
        return null
    }

    fun getFragment(position: Int): Fragment? {
        val tag = fragmentTags.get(position) ?: return null
        return fragmentManager.findFragmentByTag(tag)
    }
}