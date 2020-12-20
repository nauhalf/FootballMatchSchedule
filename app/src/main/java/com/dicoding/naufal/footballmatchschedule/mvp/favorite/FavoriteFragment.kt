package com.dicoding.naufal.footballmatchschedule.mvp.favorite

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.color.colorWhite
import com.dicoding.naufal.footballmatchschedule.utils.myToolbar
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.wrapContent

class FavoriteFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return container?.context?.let {
            return FavoriteFragmentUI().createView(AnkoContext.create(it, this))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar as Toolbar?)
        viewPager.adapter = FavoritePagerAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    inner class FavoriteFragmentUI : AnkoComponent<FavoriteFragment> {
        override fun createView(ui: AnkoContext<FavoriteFragment>): View = with(ui) {
            coordinatorLayout {
                appBarLayout {
                    lparams(width = matchParent, height = wrapContent)
                    fitsSystemWindows = true
                    toolbar = myToolbar().lparams {
                        scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS
                        width = matchParent
                    }

                    tabLayout = tabLayout {
                        id = R.id.tab_layout
                        setSelectedTabIndicatorColor(Color.WHITE)
                        tabTextColors = ContextCompat.getColorStateList(context, colorWhite)
                    }.lparams(width = matchParent) {
                        scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
                    }
                }

                viewPager = viewPager {
                    id = R.id.view_pager
                }.lparams(width = matchParent, height = matchParent) {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }
            }
        }

    }
}
