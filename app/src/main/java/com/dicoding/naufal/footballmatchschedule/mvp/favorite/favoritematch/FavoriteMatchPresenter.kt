package com.dicoding.naufal.footballmatchschedule.mvp.favorite.favoritematch

import android.content.Context
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.helper.database
import com.dicoding.naufal.footballmatchschedule.model.event.Event
import com.dicoding.naufal.footballmatchschedule.model.event.EventResponses
import com.dicoding.naufal.footballmatchschedule.model.favorite.favoritematch.FavoriteMatch
import com.dicoding.naufal.footballmatchschedule.utils.CoroutineContextProvider
import com.dicoding.naufal.footballmatchschedule.utils.EspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteMatchPresenter(private val view: FavoriteMatchView,
                             private val repo: ApiRepository,
                             private val gson: Gson,
                             private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    private fun getFavFromDb(ctx: Context): List<FavoriteMatch> {
        return ctx.database.use {
            val list = select(FavoriteMatch.TABLE_FAVORITE_MATCH).parseList(classParser<FavoriteMatch>())
            list
        }
    }

    fun getFavMatch(progressbar: Boolean, ctx: Context) {
        if (progressbar)
            view.showLoading()
        val list = getFavFromDb(ctx)
        var eventList: MutableList<Event> = mutableListOf()
        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main) {

            for (f in list) {
                val data = gson.fromJson(repo.doRequest(
                        TheSportsDbApi
                                .getMatchDetail(f.idEvent)
                ).await(),
                        EventResponses::class.java
                )

                data.events?.get(0)?.let { eventList.add(it) }
            }
            if (progressbar)
                view.hideLoading()
            view.showList(eventList)
            EspressoIdlingResource.done()
        }
    }
}
