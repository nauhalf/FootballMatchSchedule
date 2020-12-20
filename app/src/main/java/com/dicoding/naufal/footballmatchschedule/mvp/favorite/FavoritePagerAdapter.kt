package com.dicoding.naufal.footballmatchschedule.mvp.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dicoding.naufal.footballmatchschedule.mvp.favorite.favoritematch.FavoriteMatchFragment
import com.dicoding.naufal.footballmatchschedule.mvp.favorite.favoriteteam.FavoriteTeamFragment

class FavoritePagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    private val list: List<Fragment> = listOf(FavoriteMatchFragment(), FavoriteTeamFragment
    ())
    private val title: List<String> = listOf("Matches", "Teams")

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Fragment = list[position]

    override fun getPageTitle(position: Int): CharSequence? = title[position]

}