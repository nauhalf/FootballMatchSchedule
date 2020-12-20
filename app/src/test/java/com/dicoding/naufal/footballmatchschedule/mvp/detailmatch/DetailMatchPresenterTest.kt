package com.dicoding.naufal.footballmatchschedule.mvp.detailmatch

import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.model.event.Event
import com.dicoding.naufal.footballmatchschedule.model.event.EventResponses
import com.dicoding.naufal.footballmatchschedule.model.team.Team
import com.dicoding.naufal.footballmatchschedule.model.team.TeamResponses
import com.dicoding.naufal.footballmatchschedule.utils.TestContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DetailMatchPresenterTest {

    @Mock
    private lateinit var matchView: DetailMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var matchPresenter: DetailMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        matchPresenter = DetailMatchPresenter(matchView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchDetail() {
        val event: MutableList<Event> = mutableListOf()
        val homeTeam: MutableList<Team> = mutableListOf()
        val awayTeam: MutableList<Team> = mutableListOf()
        val eventResponses = EventResponses(event)
        val homeResponse = TeamResponses(homeTeam)
        val awayResponses = TeamResponses(awayTeam)
        val idEvent = "441613"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi
                            .getMatchDetail(idEvent)).await(),
                    EventResponses::class.java)).thenReturn(eventResponses)


            `when`(gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi
                            .getTeamById(event[0].homeId)).await(),
                    TeamResponses::class.java)).thenReturn(homeResponse)


            `when`(gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi
                            .getTeamById(event[0].awayId)).await(),
                    TeamResponses::class.java)).thenReturn(awayResponses)


            matchPresenter.getMatchDetail(idEvent)
            Mockito.verify(matchView).showLoading()
            Mockito.verify(matchView).showMatch(event[0], homeTeam[0], awayTeam[0])
            Mockito.verify(matchView).hideLoading()
        }
    }
}