package com.dicoding.naufal.footballmatchschedule.mvp.searchmatch

import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.model.event.EventResponses
import com.dicoding.naufal.footballmatchschedule.utils.CoroutineContextProvider
import com.dicoding.naufal.footballmatchschedule.utils.EspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchMatchPresenter(private val view: SearchMatchView,
                           private val repo: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun searchMatch(query: String?) {
        view.showLoading()
        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main) {
            val events = GlobalScope.async {
                gson.fromJson(repo.doRequest(TheSportsDbApi
                        .searchMatches(query)).await(),
                        EventResponses::class.java)
            }

            val filterEvent = events.await().events?.filter {
                it.sport == "Soccer"
            }

            view.showResults(filterEvent ?: mutableListOf())

            view.hideLoading()
            EspressoIdlingResource.done()
        }
    }
}