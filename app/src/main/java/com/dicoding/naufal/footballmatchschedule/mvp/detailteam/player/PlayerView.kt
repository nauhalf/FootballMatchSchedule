package com.dicoding.naufal.footballmatchschedule.mvp.detailteam.player

import com.dicoding.naufal.footballmatchschedule.model.player.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showList(players: List<Player>)
}