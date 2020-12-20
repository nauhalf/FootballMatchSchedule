package com.dicoding.naufal.footballmatchschedule.mvp.detailmatch

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.drawable.*
import com.dicoding.naufal.footballmatchschedule.R.id.add_to_favorite
import com.dicoding.naufal.footballmatchschedule.R.menu.detail_menu
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.model.event.Event
import com.dicoding.naufal.footballmatchschedule.model.team.Team
import com.dicoding.naufal.footballmatchschedule.utils.DateTimeUtilities
import com.dicoding.naufal.footballmatchschedule.utils.invisible
import com.dicoding.naufal.footballmatchschedule.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {
    private lateinit var presenter: DetailMatchPresenter
    private lateinit var idEvent: String
    private lateinit var event: Event
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        setup()
        presenter.getMatchDetail(idEvent)
        idEvent.let {
            isFavorite = presenter.stateFavorite(this.applicationContext, it)
        }
    }

    private fun setup() {
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        val intent = intent
        idEvent = intent.getStringExtra("idEvent")
        presenter = DetailMatchPresenter(this, ApiRepository(), Gson())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId.let { it }) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (!isFavorite) {
                    if (::event.isInitialized) {
                        presenter.addToFavorite(event, this.applicationContext, root)
                        isFavorite = !isFavorite
                    }
                } else {
                    presenter.removeFromFavorite(this.applicationContext, idEvent, root)
                    isFavorite = !isFavorite
                }

                setFavorite()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progress_layout.visible()
    }

    override fun hideLoading() {
        progress_layout.invisible()
    }

    override fun showMatch(event: Event, home: Team, away: Team) {

        this.event = event
        val date = DateTimeUtilities.dateParsing(event.date, event.time)
        txt_date.text = DateTimeUtilities.dateFormat(date)
        txt_time.text = DateTimeUtilities.timeFormat(date)
        txt_home_team.text = event.homeTeam
        txt_home_goals.text = event.homeGoalsDetails?.replace(",", "\n")
        txt_home_score.text = event.homeScore
        txt_home_shots.text = event.homeShots
        txt_home_reds.text = event.homeRedCards?.replace(";", newValue = ";\n")
        txt_home_yellows.text = event.homeYellowCards?.replace(";", newValue = ";\n")
        txt_home_goal_keeper.text = event.homeLineupGoalKeeper
        txt_home_defense.text = event.homeLineupDefense?.replace("; ", ";\n")
        txt_home_midfield.text = event.homeLineupMidfield?.replace("; ", ";\n")
        txt_home_forward.text = event.homeLineupForward?.replace("; ", ";\n")
        txt_home_substitutes.text = event.homeLineupSubstitutes?.replace("; ", ";\n")

        txt_away_team.text = event.awayTeam
        txt_away_goals.text = event.awayGoalsDetails?.replace(",", "\n")
        txt_away_score.text = event.awayScore
        txt_away_shots.text = event.awayShots
        txt_away_reds.text = event.awayRedCards?.replace(";", newValue = ";\n")
        txt_away_yellows.text = event.awayYellowCards?.replace(";", newValue = ";\n")
        txt_away_goal_keeper.text = event.awayLineupGoalKeeper
        txt_away_defense.text = event.awayLineupDefense?.replace("; ", ";\n")
        txt_away_midfield.text = event.awayLineupMidfield?.replace("; ", ";\n")
        txt_away_forward.text = event.awayLineupForward?.replace("; ", ";\n")
        txt_away_substitutes.text = event.awayLineupSubstitutes?.replace("; ", ";\n")


        home.teamBadge?.let {
            if (it.isEmpty())
                Picasso.get().load(no_image).into(img_home_team)
            else
                Picasso.get().load(it).into(img_home_team)
        }
        away.teamBadge?.let {
            if (it.isEmpty())
                Picasso.get().load(no_image).into(img_away_team)
            else
                Picasso.get().load(it).into(img_away_team)
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
