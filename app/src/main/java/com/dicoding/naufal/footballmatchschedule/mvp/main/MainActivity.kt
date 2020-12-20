package com.dicoding.naufal.footballmatchschedule.mvp.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.id.*
import com.dicoding.naufal.footballmatchschedule.mvp.favorite.FavoriteFragment
import com.dicoding.naufal.footballmatchschedule.mvp.match.MatchFragment
import com.dicoding.naufal.footballmatchschedule.mvp.team.TeamFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup(savedInstanceState)
    }

    private fun setup(savedInstanceState: Bundle?) {

        bottom_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                matchs_menu -> {
                    loadFragment(MatchFragment(), MatchFragment::class.java.simpleName, savedInstanceState)
                }
                teams_menu -> {
                    loadFragment(TeamFragment(), TeamFragment::class.java.simpleName, savedInstanceState)
                }
                favorites_menu -> {
                    loadFragment(FavoriteFragment(), FavoriteFragment::class.java.simpleName, savedInstanceState)
                }
            }
            true
        }
        bottom_nav.selectedItemId = matchs_menu
    }

    private fun loadFragment(fragment: Fragment, simpleName: String?, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.root_container, fragment, simpleName)
                    .commit()
        }
    }
}
