package com.dicoding.naufal.footballmatchschedule.mvp.match.nextmatch

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.color.colorAccent
import com.dicoding.naufal.footballmatchschedule.adapter.MatchAdapter
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.model.event.Event
import com.dicoding.naufal.footballmatchschedule.mvp.detailmatch.DetailMatchActivity
import com.dicoding.naufal.footballmatchschedule.utils.invisible
import com.dicoding.naufal.footballmatchschedule.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class NextMatchFragment : Fragment(), NextMatchView {
    private var list: MutableList<Event> = mutableListOf()
    private lateinit var presenter: NextMatchPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var idLeague: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
    }

    override fun showLoading() {

        progress_layout?.visible()
    }

    override fun hideLoading() {
        progress_layout?.invisible()
    }

    override fun showList(event: List<Event>) {
        swipe_refresh?.isRefreshing = false
        list.clear()
        list.addAll(event)
        adapter.notifyDataSetChanged()
    }

    private fun setUp() {
        presenter = NextMatchPresenter(this, ApiRepository(), Gson())

        val spinnerItem = resources.getStringArray(R.array.league_name)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItem)
        val leagueId = resources.getStringArray(R.array.league_id)
        spinner_league.adapter = spinnerAdapter

        spinner_league.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idLeague = leagueId[spinner_league.selectedItemPosition]
                presenter.getEventList(true, idLeague)
            }

        }

        adapter = MatchAdapter(list) {
            startActivity<DetailMatchActivity>("idEvent" to "${it.idEvent}")
        }

        recycler_view?.adapter = adapter
        recycler_view?.layoutManager = LinearLayoutManager(context)

        swipe_refresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), colorAccent),
                ContextCompat.getColor(requireContext(), android.R.color.holo_green_light),
                ContextCompat.getColor(requireContext(), android.R.color.holo_orange_light),
                ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        swipe_refresh?.onRefresh {
            presenter.getEventList(false, idLeague)
        }
    }
}
