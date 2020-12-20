package com.dicoding.naufal.footballmatchschedule.mvp.match.lastmatch

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import android.widget.*
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.color.*
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.model.event.Event
import com.dicoding.naufal.footballmatchschedule.mvp.detailmatch.DetailMatchActivity
import com.dicoding.naufal.footballmatchschedule.adapter.MatchAdapter
import com.dicoding.naufal.footballmatchschedule.utils.invisible
import com.dicoding.naufal.footballmatchschedule.utils.visible
import com.google.gson.Gson

import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class LastMatchFragment : Fragment(), LastMatchView {

    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private var list: MutableList<Event> = mutableListOf()
    private lateinit var adapter: MatchAdapter
    private lateinit var presenter: LastMatchPresenter
    private lateinit var spinner: Spinner
    private lateinit var idLeague: String
    private lateinit var containerProgressBar: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return container?.context?.let {

            return LastMatchFragmentUI().createView(AnkoContext.create(it, this))
        }
    }

    override fun showLoading() {
        containerProgressBar.visible()
    }

    override fun hideLoading() {
        containerProgressBar.invisible()
    }

    override fun showList(event: List<Event>) {
        swipe.isRefreshing = false
        list.clear()
        list.addAll(event)

        adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        presenter = LastMatchPresenter(this, ApiRepository(), Gson())
        val spinnerItem = resources.getStringArray(R.array.league_name)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItem)
        val leagueId = resources.getStringArray(R.array.league_id)

        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idLeague = leagueId[spinner.selectedItemPosition]
                presenter.getEventList(true, idLeague)
            }

        }
        adapter = MatchAdapter(list) {
            startActivity<DetailMatchActivity>("idEvent" to "${it.idEvent}")
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)


        swipe.onRefresh {
            presenter.getEventList(false, idLeague)
        }
    }

    inner class LastMatchFragmentUI : AnkoComponent<Fragment> {
        override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = matchParent)
                orientation = LinearLayout.VERTICAL

                swipe = swipeRefreshLayout {
                    setColorSchemeResources(colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            colorPrimary)

                    constraintLayout {
                        id = R.id.root_constraint
                        lparams(width = matchParent, height = matchParent)

                        spinner = spinner {
                            id = R.id.spinner_league
                        }.lparams(width = matchParent, height = wrapContent) {
                            topToTop = PARENT_ID
                            margin = resources.getDimensionPixelSize(R.dimen.common_padding)
                        }

                        recyclerView = recyclerView {
                            id = R.id.recycler_view
                            clipToPadding = false
                            scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
                        }.lparams(width = matchParent, height = dip(0)) {
                            topToBottom = R.id.spinner_league
                            bottomToBottom = PARENT_ID
                            topMargin = resources.getDimensionPixelSize(R.dimen.common_padding)
                            bottomPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
                        }

                        containerProgressBar = constraintLayout {
                            background = ContextCompat.getDrawable(context, colorBackground)
                            progressBar {
                                background = ContextCompat.getDrawable(context, colorBackground)
                            }.lparams(width = wrapContent, height = wrapContent) {
                                topToTop = PARENT_ID
                                leftToLeft = PARENT_ID
                                rightToRight = PARENT_ID
                                bottomToBottom = PARENT_ID
                            }
                        }.lparams(width = matchParent, height = dip(0)) {
                            topToBottom = R.id.spinner_league
                            bottomToBottom = PARENT_ID
                        }
                    }
                }
            }
        }
    }
}
