package com.dicoding.naufal.footballmatchschedule.mvp.detailmatch

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.view.View
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.helper.database
import com.dicoding.naufal.footballmatchschedule.model.event.Event
import com.dicoding.naufal.footballmatchschedule.model.event.EventResponses
import com.dicoding.naufal.footballmatchschedule.model.favorite.favoritematch.FavoriteMatch
import com.dicoding.naufal.footballmatchschedule.model.team.TeamResponses
import com.dicoding.naufal.footballmatchschedule.utils.CoroutineContextProvider
import com.dicoding.naufal.footballmatchschedule.utils.EspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailMatchPresenter(private val view: DetailMatchView,
                           private val repo: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getMatchDetail(idEvent: String?) {
        view.showLoading()

        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main) {
            val event = GlobalScope.async {
                gson.fromJson(repo.doRequest(TheSportsDbApi
                        .getMatchDetail(idEvent)).await(),
                        EventResponses::class.java)
            }

            val homeTeam = GlobalScope.async {
                gson.fromJson(repo.doRequest(
                        TheSportsDbApi
                                .getTeamById(event.await().events?.get(0)?.homeId)
                ).await(),
                        TeamResponses::class.java
                )
            }

            val awayTeam = GlobalScope.async {
                gson.fromJson(repo.doRequest(
                        TheSportsDbApi
                                .getTeamById(event.await().events?.get(0)?.awayId)
                ).await(),
                        TeamResponses::class.java
                )
            }


            event.await().events?.get(0)?.let { e ->
                homeTeam.await().teams?.get(0)?.let { h ->
                    awayTeam.await().teams?.get(0)?.let { a ->
                        view.showMatch(e, h, a)
                    }
                }
            }
            view.hideLoading()

            EspressoIdlingResource.done()
        }
    }

    fun addToFavorite(event: Event, ctx: Context, view: View) {
        try {
            EspressoIdlingResource.begin()
            GlobalScope.launch {
                ctx.database.use {
                    insert(FavoriteMatch.TABLE_FAVORITE_MATCH,
                            FavoriteMatch.ID_EVENT to event.idEvent)

                }

                view.snackbar("Added to favorite").show()
                EspressoIdlingResource.done()
            }
        } catch (e: SQLiteException) {
            view.snackbar(e.localizedMessage).show()
        }
    }

    fun removeFromFavorite(ctx: Context, id: String, view: View) {
        try {
            EspressoIdlingResource.begin()
            GlobalScope.launch {
                ctx.database.use {
                    delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(ID_EVENT = {id})", "id" to id)
                }
                view.snackbar("Remove from favorite").show()
                EspressoIdlingResource.done()
            }
        } catch (e: SQLiteConstraintException) {
            view.snackbar(e.localizedMessage).show()
        } catch (e: SQLiteException) {
            view.snackbar(e.localizedMessage).show()
        }
    }

    fun stateFavorite(ctx: Context, id: String): Boolean {
        var isFavorite = false
        ctx.database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                    .whereArgs("(ID_EVENT) = {id}", "id" to id)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            isFavorite = !favorite.isEmpty()
        }
        return isFavorite
    }
}