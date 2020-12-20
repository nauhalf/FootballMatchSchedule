package com.dicoding.naufal.footballmatchschedule.mvp.detailmatch

import com.dicoding.naufal.footballmatchschedule.model.event.Event
import com.dicoding.naufal.footballmatchschedule.model.team.Team

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatch(event: Event, home : Team, away : Team)
}