package com.dicoding.naufal.footballmatchschedule.mvp.favorite.favoritematch

import com.dicoding.naufal.footballmatchschedule.model.event.Event


interface FavoriteMatchView {
    fun showLoading()
    fun hideLoading()
    fun showList(event: List<Event>)
}