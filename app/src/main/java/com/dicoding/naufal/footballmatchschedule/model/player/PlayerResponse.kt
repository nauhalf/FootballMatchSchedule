package com.dicoding.naufal.footballmatchschedule.model.player

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
        @SerializedName(value = "player", alternate = ["players"])
        val player: List<Player>? = null)