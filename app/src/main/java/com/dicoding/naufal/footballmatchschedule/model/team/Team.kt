package com.dicoding.naufal.footballmatchschedule.model.team

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null,

        @SerializedName("intFormedYear")
        var formedYear: String? = null,

        @SerializedName("strStadium")
        var stadiumName: String? = null,

        @SerializedName("strDescriptionEN")
        var descriptionEN: String? = null,

        @SerializedName("strSport")
        var sport: String? = null) : Parcelable
