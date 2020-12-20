package com.dicoding.naufal.footballmatchschedule.mvp.detailteam

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.view.View
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
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailTeamPresenter(private val view: DetailTeamView,
                          private val repo: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getTeamDetails(idTeam: String?) {

        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main) {
            val team = GlobalScope.async {
                gson.fromJson(repo.doRequest(TheSportsDbApi
                        .getTeamById(idTeam)).await(),
                        TeamResponses::class.java)
            }

            team.await().teams?.get(0)?.let { view.showTeam(it) }

            EspressoIdlingResource.done()
        }
    }

    fun addToFavorite(team: Team, context: Context, view: View) {
        try {
            context.database.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                        FavoriteTeam.ID_TEAM to team.teamId,
                        FavoriteTeam.TEAM_NAME to team.teamName,
                        FavoriteTeam.TEAM_BADGE to team.teamBadge)

            }

            view.snackbar("Added to favorite").show()
        } catch (e: SQLiteException) {
            view.snackbar(e.localizedMessage).show()
        }
    }

    fun removeFromFavorite(context: Context, id: String, view: View) {
        try {
            context.database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(ID_TEAM = {id})", "id" to id)
            }
            view.snackbar("Remove from favorite").show()
        } catch (e: SQLiteConstraintException) {
            view.snackbar(e.localizedMessage).show()
        } catch (e: SQLiteException) {
            view.snackbar(e.localizedMessage).show()
        }
    }

    fun stateFavorite(context: Context, id: String): Boolean {
        var isFavorite = false
        context.database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(ID_TEAM) = {id}", "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            isFavorite = !favorite.isEmpty()
        }
        return isFavorite
    }
}