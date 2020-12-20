package com.dicoding.naufal.footballmatchschedule.adapter

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dicoding.naufal.footballmatchschedule.R
import com.dicoding.naufal.footballmatchschedule.item.ItemMatchUI
import com.dicoding.naufal.footballmatchschedule.model.event.Event
import com.dicoding.naufal.footballmatchschedule.utils.DateTimeUtilities
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class MatchAdapter(private val events: List<Event>,
                   private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(events[position], listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val txtScheduleDate = itemView.find<TextView>(R.id.txt_schedule_date)
        private val txtHomeTeam = itemView.find<TextView>(R.id.txt_home_team)
        private val txtHomeScore = itemView.find<TextView>(R.id.txt_home_score)
        private val txtAwayTeam = itemView.find<TextView>(R.id.txt_away_team)
        private val txtAwayScore = itemView.find<TextView>(R.id.txt_away_score)
        private val txtScheduleTime = itemView.find<TextView>(R.id.txt_schedule_time)
        private val cardItem = itemView.find<CardView>(R.id.card_item)
        fun bindItems(events: Event, listener: (Event) -> Unit) {
            val date = DateTimeUtilities.dateParsing(events.date, events.time)
            txtScheduleDate.text = DateTimeUtilities.dateFormat(date)
            txtScheduleTime.text = DateTimeUtilities.timeFormat(date)
            txtHomeTeam.text = events.homeTeam
            txtHomeScore.text = events.homeScore
            txtAwayTeam.text = events.awayTeam
            txtAwayScore.text = events.awayScore

            cardItem.setOnClickListener {
                listener(events)
            }
        }
    }
}