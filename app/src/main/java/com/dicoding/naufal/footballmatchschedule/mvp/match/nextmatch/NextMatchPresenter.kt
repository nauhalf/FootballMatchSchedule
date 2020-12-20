package com.dicoding.naufal.footballmatchschedule.mvp.match.nextmatch

import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.model.event.EventResponses
import com.dicoding.naufal.footballmatchschedule.utils.CoroutineContextProvider
import com.dicoding.naufal.footballmatchschedule.utils.EspressoIdlingResource
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter(private val view: NextMatchView,
                         private val repo: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getEventList(progressbar: Boolean, idLeague: String?){
        if(progressbar)
            view.showLoading()
        EspressoIdlingResource.begin()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(repo.
                    doRequest(
                            TheSportsDbApi
                                    .getNextMatch(idLeague)
                    ).await(),
                    EventResponses::class.java
            )

            if(progressbar)
                view.hideLoading()

            data.events?.let { view.showList(it) }
            EspressoIdlingResource.done()
        }
    }
}