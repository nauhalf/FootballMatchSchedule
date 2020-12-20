package com.dicoding.naufal.footballmatchschedule.mvp.detailplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.api.ApiRepository
import com.dicoding.naufal.footballmatchschedule.model.player.Player
import com.dicoding.naufal.footballmatchschedule.utils.invisible
import com.dicoding.naufal.footballmatchschedule.utils.measurementOrNull
import com.dicoding.naufal.footballmatchschedule.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity(), DetailPlayerView {
    private lateinit var presenter: DetailPlayerPresenter
    private lateinit var idPlayer: String
    private lateinit var player: Player
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        setup()

        presenter.getPlayerDetails(idPlayer)
    }

    private fun setup() {
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        val intent = intent
        idPlayer = intent.getStringExtra("idPlayer")
        presenter = DetailPlayerPresenter(this, ApiRepository(), Gson())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId.let { it }) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showPlayer(player: Player) {
        this.player = player
        supportActionBar?.title = player.playerName
        txt_player_weight.text = measurementOrNull(player.playerWeight)
        txt_player_height.text = measurementOrNull(player.playerHeight)
        txt_player_description.text = player.playerDescriptionEN
        txt_position.text = player.playerPosition
        player.playerThumbnail?.let {
            if (it.isEmpty())
                Picasso.get().load(R.drawable.no_image).into(img_player)
            else
                Picasso.get().load(it).into(img_player)
        }
    }

    override fun showLoading() {
        progress_layout.visible()
    }

    override fun hideLoading() {
        progress_layout.invisible()
    }
}
