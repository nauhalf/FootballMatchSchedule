package com.dicoding.naufal.footballmatchschedule.mvp.team

import com.dicoding.naufal.footballmatchschedule.model.team.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun searchModeOn()
    fun searchModeOff()
    fun showList(teams: List<Team>)
}