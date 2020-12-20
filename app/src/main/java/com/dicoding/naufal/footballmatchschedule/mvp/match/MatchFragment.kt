package com.dicoding.naufal.footballmatchschedule.mvp.match

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
import android.view.*
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.color.colorWhite
import com.dicoding.naufal.footballmatchschedule.R.id.search_match
import com.dicoding.naufal.footballmatchschedule.mvp.match.adapter.MatchPagerAdapter
import com.dicoding.naufal.footballmatchschedule.mvp.searchmatch.SearchMatchActivity
import com.dicoding.naufal.footballmatchschedule.utils.myToolbar
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.viewPager

class MatchFragment : Fragment() {

    private lateinit var tabLayout : TabLayout
    private lateinit var toolbar : Toolbar
    private lateinit var viewPager : ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return container?.context?.let{
            return MatchFragmentUI().createView(AnkoContext.create(it, this))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.match_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId.let { it }){
            search_match -> {
                startActivity<SearchMatchActivity>()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUp(){
        (activity as AppCompatActivity).setSupportActionBar(toolbar as Toolbar?)
        viewPager.adapter = MatchPagerAdapter(childFragmentManager, requireContext())
        tabLayout.setupWithViewPager(viewPager)
        setHasOptionsMenu(true)
    }

    inner class MatchFragmentUI : AnkoComponent<MatchFragment> {
        override fun createView(ui: AnkoContext<MatchFragment>): View = with(ui) {
            coordinatorLayout {
                appBarLayout {
                    lparams(width = matchParent, height = wrapContent)
                    fitsSystemWindows = true
                    toolbar = myToolbar().lparams{
                        scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS
                        width = matchParent
                    }

                    tabLayout = tabLayout {
                        id = R.id.tab_layout
                        setSelectedTabIndicatorColor(Color.WHITE)
                        tabTextColors = ContextCompat.getColorStateList(context, colorWhite)
                    }.lparams(width = matchParent){
                        scrollFlags = SCROLL_FLAG_ENTER_ALWAYS
                    }
                }

                viewPager = viewPager {
                    id = R.id.view_pager
                }.lparams(width = matchParent, height = matchParent){
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }
            }
        }

    }
}
