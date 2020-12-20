package com.dicoding.naufal.footballmatchschedule.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.item.ItemTeamUI
import com.dicoding.naufal.footballmatchschedule.model.team.Team
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class TeamAdapter(private val teams: List<Team>,
                  private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(teams[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val txtHomeTeam = itemView.find<TextView>(R.id.txt_team_name)
        private val imgTeamLogo = itemView.find<ImageView>(R.id.img_team_logo)
        fun bindItems(team: Team, listener: (Team) -> Unit) {
            txtHomeTeam.text = team.teamName

            team.teamBadge?.let {
                if (it.isEmpty())
                    Picasso.get().load(R.drawable.no_image).into(imgTeamLogo)
                else
                    Picasso.get().load(it).into(imgTeamLogo)
            }

            itemView.setOnClickListener {
                listener(team)
            }
        }
    }
}