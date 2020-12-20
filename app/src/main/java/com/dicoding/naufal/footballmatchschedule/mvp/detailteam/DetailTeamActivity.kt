package com.dicoding.naufal.footballmatchschedule.mvp.detailteam

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.drawable.ic_add_to_favorites
import com.dicoding.naufal.footballmatchschedule.R.drawable.ic_added_to_favorites
import com.dicoding.naufal.footballmatchschedule.R.id.add_to_favorite
import com.dicoding.naufal.footballmatchschedule.R.menu.detail_menu
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.model.team.Team
import com.dicoding.naufal.footballmatchschedule.mvp.detailteam.adapter.DetailTeamPagerAdapter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {
    private lateinit var presenter: DetailTeamPresenter
    private lateinit var idTeam: String
    private lateinit var team: Team
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        setup()
        idTeam.let {
            isFavorite = presenter.stateFavorite(this.applicationContext, it)
        }
    }

    private fun setup() {
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = null


        val intent = intent
        idTeam = intent.getStringExtra("idTeam")
        presenter = DetailTeamPresenter(this, ApiRepository(), Gson())
        presenter.getTeamDetails(idTeam)
        view_pager.adapter = DetailTeamPagerAdapter(supportFragmentManager, idTeam)
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId.let { it }) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (!isFavorite) {
                    if (::team.isInitialized) {
                        presenter.addToFavorite(team, this.applicationContext, root)
                        isFavorite = !isFavorite
                    }
                } else {
                    presenter.removeFromFavorite(this.applicationContext, idTeam, root)
                    isFavorite = !isFavorite
                }

                setFavorite()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showTeam(team: Team) {
        this.team = team

        txt_team_name.text = team.teamName
        txt_formed_year.text = team.formedYear
        txt_stadium.text = team.stadiumName

        team.teamBadge?.let {
            if (it.isEmpty())
                Picasso.get().load(R.drawable.no_image).into(img_team_logo)
            else
                Picasso.get().load(it).into(img_team_logo)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}
