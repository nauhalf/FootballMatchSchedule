package com.dicoding.naufal.footballmatchschedule.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.R.id.*
import com.dicoding.naufal.footballmatchschedule.item.ItemPlayerUI
import com.dicoding.naufal.footballmatchschedule.model.player.Player
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class PlayerAdapter(private val players: List<Player>,
                    private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(players[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val txtPlayerName = itemView.find<TextView>(txt_player_name)
        private val imgPlayer = itemView.find<ImageView>(img_player)
        private val txtPlayerPosition = itemView.find<TextView>(txt_player_position)
        fun bindItems(player: Player, listener: (Player) -> Unit) {
            txtPlayerName.text = player.playerName
            txtPlayerPosition.text = player.playerPosition
            player.playerCutout?.let {
                if (it.isEmpty())
                    Picasso.get().load(R.drawable.no_image).into(imgPlayer)
                else
                    Picasso.get().load(it).into(imgPlayer)
            }

            itemView.setOnClickListener {
                listener(player)
            }
        }
    }
}