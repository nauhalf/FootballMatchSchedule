package com.dicoding.naufal.footballmatchschedule.mvp.detailteam

import com.dicoding.naufal.footballmatchschedule.model.team.Team

interface DetailTeamView {
    fun showTeam(team: Team)
}