package com.dicoding.naufal.footballmatchschedule.mvp.detailteam.player

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.color.colorBackground
import com.dicoding.naufal.footballmatchschedule.adapter.PlayerAdapter
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.model.player.Player
import com.dicoding.naufal.footballmatchschedule.mvp.detailplayer.DetailPlayerActivity
import com.dicoding.naufal.footballmatchschedule.utils.invisible
import com.dicoding.naufal.footballmatchschedule.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.startActivity

class PlayerFragment : Fragment(), PlayerView {
    private lateinit var recyclerView: RecyclerView
    private var list: MutableList<Player> = mutableListOf()
    private lateinit var adapter: PlayerAdapter
    private lateinit var presenter: PlayerPresenter
    private lateinit var idTeam: String
    private lateinit var containerProgressBar: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return container?.context?.let {
            return PlayerFragmentUI().createView(AnkoContext.create(it, this))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idTeam = this.arguments!!.getString("idTeam")
    }

    override fun showLoading() {
        containerProgressBar.visible()
    }

    override fun hideLoading() {
        containerProgressBar.invisible()
    }

    override fun showList(players: List<Player>) {
        list.clear()
        list.addAll(players)
        adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        presenter = PlayerPresenter(this, ApiRepository(), Gson())

        adapter = PlayerAdapter(list) {
            startActivity<DetailPlayerActivity>("idPlayer" to it.playerId)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        presenter.getPlayerList(idTeam)
    }

    companion object {
        fun newInstance(idTeam: String?) = PlayerFragment().apply {
            arguments = Bundle().apply {
                putString("idTeam", idTeam)
            }
        }
    }

    inner class PlayerFragmentUI : AnkoComponent<Fragment> {
        override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = matchParent)
                orientation = LinearLayout.VERTICAL
                isClickable = true


                constraintLayout {
                    id = R.id.root_constraint
                    lparams(width = matchParent, height = matchParent)


                    recyclerView = recyclerView {
                        id = R.id.recycler_view
                        clipToPadding = false
                        scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
                    }.lparams(width = matchParent, height = dip(0)) {
                        topToTop = PARENT_ID
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
