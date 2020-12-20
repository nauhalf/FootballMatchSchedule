package com.dicoding.naufal.footballmatchschedule.mvp.match.nextmatch

import com.dicoding.naufal.footballmatchschedule.model.event.Event

interface NextMatchView {
    fun showLoading()
    fun hideLoading()
    fun showList(event: List<Event>)
}