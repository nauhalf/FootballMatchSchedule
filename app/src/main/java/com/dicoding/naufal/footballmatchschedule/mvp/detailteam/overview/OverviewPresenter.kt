package com.dicoding.naufal.footballmatchschedule.mvp.detailteam.overview

import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.model.team.TeamResponses
import com.dicoding.naufal.footballmatchschedule.utils.CoroutineContextProvider
import com.dicoding.naufal.footballmatchschedule.utils.EspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class OverviewPresenter(private val view: OverviewView,
                        private val repo: ApiRepository,
                        private val gson: Gson,
                        private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getDescription(idTeam: String?) {
        view.showLoading()
        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main) {
            val team = GlobalScope.async {
                gson.fromJson(repo.doRequest(TheSportsDbApi
                        .getTeamById(idTeam)).await(),
                        TeamResponses::class.java)
            }

            view.showOverview(team.await().teams?.get(0)?.descriptionEN)

            view.hideLoading()
            EspressoIdlingResource.done()
        }
    }
}