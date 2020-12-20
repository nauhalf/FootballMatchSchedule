package com.dicoding.naufal.footballmatchschedule.mvp.favorite.favoriteteam

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.color.colorAccent
import com.dicoding.naufal.footballmatchschedule.R.color.colorPrimary
import com.dicoding.naufal.footballmatchschedule.adapter.TeamAdapter
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.model.team.Team
import com.dicoding.naufal.footballmatchschedule.mvp.detailteam.DetailTeamActivity
import com.dicoding.naufal.footballmatchschedule.utils.invisible
import com.dicoding.naufal.footballmatchschedule.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteTeamFragment : Fragment(), FavoriteTeamView {

    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var list: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamAdapter
    private lateinit var presenter: FavoriteTeamPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return container?.context?.let {
            val view = FavoriteTeamFragmentUI().createView(AnkoContext.create(it, this))

            setup()

            return view
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = TeamAdapter(list) {
            startActivity<DetailTeamActivity>("idTeam" to "${it.teamId}")
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showList(team: List<Team>) {
        swipe.isRefreshing = false
        list.clear()
        list.addAll(team)

        adapter.notifyDataSetChanged()
    }

    private fun setup() {
        presenter = FavoriteTeamPresenter(this, ApiRepository(), Gson())
        presenter.getFavMatch(true, this.requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        swipe.onRefresh {
            presenter.getFavMatch(false, this.requireContext())
        }
    }

    inner class FavoriteTeamFragmentUI : AnkoComponent<Fragment> {
        override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = matchParent)
                orientation = LinearLayout.VERTICAL
                isClickable = true

                swipe = swipeRefreshLayout {
                    id = R.id.swipe
                    setColorSchemeResources(colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            colorPrimary)


                    relativeLayout {
                        lparams(width = matchParent, height = matchParent)
                        recyclerView = recyclerView {
                            id = R.id.recycler_view
                            scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
                            clipToPadding = false
                            lparams(width = matchParent, height = matchParent) {
                                bottomPadding = resources.getDimensionPixelSize(R.dimen.common_padding)
                            }
                        }
                        progressBar = progressBar {

                        }.lparams {
                            centerInParent()
                        }
                    }
                }
            }
        }
    }
}
