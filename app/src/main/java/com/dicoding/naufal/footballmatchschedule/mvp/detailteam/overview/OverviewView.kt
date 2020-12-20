package com.dicoding.naufal.footballmatchschedule.mvp.detailteam.overview


interface OverviewView {
    fun showLoading()
    fun hideLoading()
    fun showOverview(description: String?)
}