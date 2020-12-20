package com.dicoding.naufal.footballmatchschedule.mvp.detailplayer

import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.model.player.PlayerResponse
import com.dicoding.naufal.footballmatchschedule.utils.CoroutineContextProvider
import com.dicoding.naufal.footballmatchschedule.utils.EspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailPlayerPresenter(private val view: DetailPlayerView,
                            private val repo: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getPlayerDetails(idTeam: String?) {
        view.showLoading()
        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main) {
            val player = GlobalScope.async {
                gson.fromJson(repo.doRequest(TheSportsDbApi
                        .getPlayerDetail(idTeam)).await(),
                        PlayerResponse::class.java)
            }


            player.await().player?.get(0)?.let { view.showPlayer(it) }
            view.hideLoading()
            EspressoIdlingResource.done()
        }
    }
}