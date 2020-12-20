package com.dicoding.naufal.footballmatchschedule.mvp.searchmatch

import com.dicoding.naufal.footballmatchschedule.model.event.Event


interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun showResults(events: List<Event>)
}