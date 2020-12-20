package com.dicoding.naufal.footballmatchschedule.mvp.favorite.favoriteteam

import android.content.Context
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.helper.database
import com.dicoding.naufal.footballmatchschedule.model.favorite.favoriteteam.FavoriteTeam
import com.dicoding.naufal.footballmatchschedule.model.team.Team
import com.dicoding.naufal.footballmatchschedule.model.team.TeamResponses
import com.dicoding.naufal.footballmatchschedule.utils.CoroutineContextProvider
import com.dicoding.naufal.footballmatchschedule.utils.EspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTeamPresenter(private val view: FavoriteTeamView,
                            private val repo: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    private fun getFavFromDb(ctx: Context): List<FavoriteTeam> {
        var returnList: MutableList<FavoriteTeam> = mutableListOf()

        EspressoIdlingResource.begin()
        ctx.database.use {
            val list = select(FavoriteTeam.TABLE_FAVORITE_TEAM).parseList(classParser<FavoriteTeam>())
            returnList.addAll(list)
        }

        EspressoIdlingResource.done()
        return returnList
    }

    fun getFavMatch(progressbar: Boolean, ctx: Context) {
        if (progressbar)
            view.showLoading()
        val list = getFavFromDb(ctx)
        var teamList: MutableList<Team> = mutableListOf()
        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main) {

            for (f in list) {
                val data = GlobalScope.async {
                    gson.fromJson(repo.doRequest(
                            TheSportsDbApi
                                    .getTeamById(f.idTeam)
                    ).await(),
                            TeamResponses::class.java
                    )
                }

                data.await().teams?.get(0)?.let { teamList.add(it) }
            }
            if (progressbar)
                view.hideLoading()
            view.showList(teamList)
            EspressoIdlingResource.done()
        }
    }
}
