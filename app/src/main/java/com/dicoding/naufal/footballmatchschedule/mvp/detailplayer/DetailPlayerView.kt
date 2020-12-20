package com.dicoding.naufal.footballmatchschedule.mvp.detailplayer

import com.dicoding.naufal.footballmatchschedule.model.player.Player

interface DetailPlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayer(player: Player)
}