package com.dicoding.naufal.footballmatchschedule.mvp.detailteam.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dicoding.naufal.footballmatchschedule.mvp.detailteam.overview.OverviewFragment
import com.dicoding.naufal.footballmatchschedule.mvp.detailteam.player.PlayerFragment

class DetailTeamPagerAdapter(fragmentManager: FragmentManager, idTeam: String?) : FragmentStatePagerAdapter(fragmentManager) {
    private val list: List<Fragment> = listOf(OverviewFragment.newInstance(idTeam))
    private val title: List<String> = listOf("Overview")

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Fragment = list[position]

    override fun getPageTitle(position: Int): CharSequence = title[position]

}