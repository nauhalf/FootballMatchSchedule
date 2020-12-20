package com.dicoding.naufal.footballmatchschedule.mvp.team

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.color.colorAccent
import com.dicoding.naufal.footballmatchschedule.adapter.TeamAdapter
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.model.team.Team
import com.dicoding.naufal.footballmatchschedule.mvp.detailteam.DetailTeamActivity
import com.dicoding.naufal.footballmatchschedule.utils.invisible
import com.dicoding.naufal.footballmatchschedule.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamFragment : Fragment(), TeamView {

    private lateinit var toolbar: Toolbar
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var containerProgressBar: ConstraintLayout
    private lateinit var presenter: TeamPresenter
    private lateinit var idLeague: String
    private var list: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return container?.context?.let {
            return TeamFragmentUI().createView(AnkoContext.create(it, this))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.team_search, menu)
        val item = menu.findItem(R.id.search_team)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = item?.actionView as SearchView?
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.searchTeam(newText)
                return true
            }

        })

        searchView?.setOnCloseListener {
            searchModeOff()
            searchView.onActionViewCollapsed()
            presenter.getTeamList(true, idLeague)
            true

        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUp() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar as Toolbar?)
        presenter = TeamPresenter(this, ApiRepository(), Gson())

        val spinnerItem = resources.getStringArray(R.array.league_name)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItem)
        val leagueId = resources.getStringArray(R.array.league_id)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idLeague = leagueId[spinner.selectedItemPosition]
                presenter.getTeamList(true, idLeague)
            }
        }

        adapter = TeamAdapter(list) {
            startActivity<DetailTeamActivity>("idTeam" to it.teamId)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        swipe.setColorSchemeColors(ContextCompat.getColor(requireContext(), colorAccent),
                ContextCompat.getColor(requireContext(), android.R.color.holo_green_light),
                ContextCompat.getColor(requireContext(), android.R.color.holo_orange_light),
                ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        swipe.onRefresh {
            presenter.getTeamList(false, idLeague)
        }


        setHasOptionsMenu(true)
    }

    override fun showLoading() {
        containerProgressBar.visible()
    }

    override fun hideLoading() {
        containerProgressBar.invisible()
    }

    override fun searchModeOn() {
        spinner.invisible()
        swipe.isRefreshing = false
        swipe.isEnabled = false
    }

    override fun searchModeOff() {
        spinner.visible()
        swipe.isRefreshing = true
        swipe.isEnabled = true
    }

    override fun showList(teams: List<Team>) {
        swipe.isRefreshing = false
        list.clear()
        list.addAll(teams)

        adapter.notifyDataSetChanged()
    }

    inner class TeamFragmentUI : AnkoComponent<TeamFragment> {
        override fun createView(ui: AnkoContext<TeamFragment>): View = with(ui) {
            coordinatorLayout {
                appBarLayout {
                    lparams(width = matchParent, height = wrapContent)
                    fitsSystemWindows = true
                    toolbar = themedToolbar(R.style.ThemeOverlay_AppCompat_Dark_ActionBar).lparams {
                        scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
                        width = matchParent
                    }
                }

                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    orientation = LinearLayout.VERTICAL

                    swipe = swipeRefreshLayout {
                        setColorSchemeResources(R.color.colorAccent,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                R.color.colorPrimary)

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
                                background = ContextCompat.getDrawable(context, R.color.colorBackground)
                                progressBar {
                                    background = ContextCompat.getDrawable(context, R.color.colorBackground)
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
                }.lparams(width = matchParent, height = matchParent){
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }
            }
        }
    }
}
