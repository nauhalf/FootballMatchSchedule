package com.dicoding.naufal.footballmatchschedule.mvp.match.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.mvp.match.lastmatch.LastMatchFragment
import com.dicoding.naufal.footballmatchschedule.mvp.match.nextmatch.NextMatchFragment

class MatchPagerAdapter(fragmentManager: FragmentManager, context: Context) : FragmentStatePagerAdapter(fragmentManager) {
    private val list: List<Fragment> = listOf(NextMatchFragment(), LastMatchFragment())
    private val title: List<String> = listOf(context.resources.getString(R.string.upcoming), context.getString(R.string.previous))

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Fragment = list[position]

    override fun getPageTitle(position: Int): CharSequence? = title[position]

}