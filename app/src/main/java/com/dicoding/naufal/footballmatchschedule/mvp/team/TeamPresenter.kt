package com.dicoding.naufal.footballmatchschedule.mvp.team

import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.model.team.TeamResponses
import com.dicoding.naufal.footballmatchschedule.utils.CoroutineContextProvider
import com.dicoding.naufal.footballmatchschedule.utils.EspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeamPresenter(private val view: TeamView,
                    private val repo : ApiRepository,
                    private val gson : Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(progressbar: Boolean, idLeague: String?){
        if(progressbar)
            view.showLoading()
        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(repo.
                    doRequest(
                            TheSportsDbApi
                                    .getTeamByLeagueId(idLeague)
                    ).await(),
                    TeamResponses::class.java
            )

            if(progressbar)
                view.hideLoading()
            data.teams?.let { view.showList(it) }
            EspressoIdlingResource.done()
        }
    }

    fun searchTeam(q: String?) {
        view.searchModeOn()
        view.showLoading()
        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main){
            val data = GlobalScope.async {
                gson.fromJson(repo.doRequest(TheSportsDbApi
                        .searchTeams(q)).await(),
                        TeamResponses::class.java)
            }

            val filterTeam = data.await().teams?.filter {
                it.sport == "Soccer"
            }

            if(filterTeam.isNullOrEmpty()){
                view.showList(mutableListOf())
            } else {
                filterTeam.let { view.showList(it) }
            }
            view.hideLoading()
            EspressoIdlingResource.done()

        }
    }
}