package com.dicoding.naufal.footballmatchschedule.mvp.match.lastmatch

import com.dicoding.naufal.footballmatchschedule.model.event.Event

interface LastMatchView {
    fun showLoading()
    fun hideLoading()
    fun showList(event: List<Event>)
}