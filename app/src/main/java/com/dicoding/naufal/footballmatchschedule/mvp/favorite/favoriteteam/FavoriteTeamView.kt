package com.dicoding.naufal.footballmatchschedule.mvp.favorite.favoriteteam

import com.dicoding.naufal.footballmatchschedule.model.team.Team


interface FavoriteTeamView {
    fun showLoading()
    fun hideLoading()
    fun showList(team: List<Team>)
}