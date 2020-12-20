package com.dicoding.naufal.footballmatchschedule.mvp.detailteam.player

import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.model.player.PlayerResponse
import com.dicoding.naufal.footballmatchschedule.utils.CoroutineContextProvider
import com.dicoding.naufal.footballmatchschedule.utils.EspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PlayerPresenter(private val view: PlayerView,
                      private val repo: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerList(idTeam: String?) {
        view.showLoading()
        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main) {
            val data = GlobalScope.async {
                gson.fromJson(repo.doRequest(TheSportsDbApi
                        .getPlayerByTeamId(idTeam)).await(),
                        PlayerResponse::class.java)
            }

            view.hideLoading()
            view.showList(data.await().player ?: mutableListOf())
            EspressoIdlingResource.done()
        }
    }
}