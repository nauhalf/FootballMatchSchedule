package com.dicoding.naufal.footballmatchschedule.mvp.match.nextmatch

import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.api.sportdbapi.TheSportsDbApi
import com.dicoding.naufal.footballmatchschedule.model.event.Event
import com.dicoding.naufal.footballmatchschedule.model.event.EventResponses
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

class NextMatchFragmentTest {

    @Mock
    private lateinit var view: NextMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: NextMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testShowList() {
        val data: MutableList<Event> = mutableListOf()
        val responses = EventResponses(data)
        val idLeague = "4332"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi
                            .getNextMatch(idLeague)).await(),
                    EventResponses::class.java)).thenReturn(responses)

            presenter.getEventList(true, idLeague)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showList(data)
            Mockito.verify(view).hideLoading()
        }
    }
}