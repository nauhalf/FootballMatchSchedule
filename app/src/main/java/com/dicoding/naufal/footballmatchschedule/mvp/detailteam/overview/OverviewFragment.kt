package com.dicoding.naufal.footballmatchschedule.mvp.detailteam.overview

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dicoding.naufal.footballmatchschedule.R.color.colorBackground
import com.dicoding.naufal.footballmatchschedule.R.dimen.common_padding
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.utils.invisible
import com.dicoding.naufal.footballmatchschedule.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.support.v4.nestedScrollView

class OverviewFragment : Fragment(), OverviewView {

    private lateinit var idTeam: String
    private lateinit var txtDesc: TextView
    private lateinit var presenter: OverviewPresenter
    private lateinit var containerProgressBar: ConstraintLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return container?.context?.let {
            return OverviewFragmentUI().createView(AnkoContext.create(it, this))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idTeam = this.arguments!!.getString("idTeam")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = OverviewPresenter(this, ApiRepository(), Gson())
        presenter.getDescription(idTeam)
    }

    override fun showLoading() {
        containerProgressBar.visible()
    }

    override fun hideLoading() {
        containerProgressBar.invisible()
    }

    override fun showOverview(description: String?) {
        txtDesc.text = description
    }

    companion object {
        fun newInstance(idTeam: String?) = OverviewFragment().apply {
            arguments = Bundle().apply {
                putString("idTeam", idTeam)
            }
        }
    }

    inner class OverviewFragmentUI : AnkoComponent<OverviewFragment> {
        override fun createView(ui: AnkoContext<OverviewFragment>): View = with(ui) {
            constraintLayout {
                lparams(width = matchParent, height = matchParent)

                nestedScrollView {

                    constraintLayout {

                        txtDesc = textView {

                        }.lparams(width = matchParent, height = matchParent) {
                            margin = resources.getDimensionPixelSize(common_padding)
                            topToTop = PARENT_ID
                            bottomToBottom = PARENT_ID
                            leftToLeft = PARENT_ID
                            rightToRight = PARENT_ID
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
                        }.lparams(width = matchParent, height = matchParent) {
                            topToBottom = PARENT_ID
                            bottomToBottom = PARENT_ID
                        }

                    }.lparams(width = matchParent, height = matchParent)

                }.lparams(width = matchParent, height = matchParent)
            }
        }

    }
}
