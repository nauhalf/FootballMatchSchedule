package com.dicoding.naufal.footballmatchschedule.model.event

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(

        @SerializedName("idEvent")
        val idEvent: String?,

        @SerializedName("dateEvent")
        var date: String? = null,

        @SerializedName("strTime")
        var time: String? = null,

        //start home section
        @SerializedName("idHomeTeam")
        var homeId: String? = null,

        @SerializedName("strHomeTeam")
        var homeTeam: String? = null,

        @SerializedName("intHomeScore")
        var homeScore: String? = null,

        @SerializedName("strHomeGoalDetails")
        var homeGoalsDetails: String? = null,

        @SerializedName("intHomeShots")
        var homeShots: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var homeLineupGoalKeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var homeLineupDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var homeLineupMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var homeLineupForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var homeLineupSubstitutes: String? = null,

        @SerializedName("strHomeRedCards")
        var homeRedCards: String? = null,

        @SerializedName("strHomeYellowCards")
        var homeYellowCards: String? = null,
        //end of home section

        //start away section
        @SerializedName("idAwayTeam")
        var awayId: String? = null,

        @SerializedName("strAwayTeam")
        var awayTeam: String? = null,


        @SerializedName("intAwayScore")
        var awayScore: String? = null,

        @SerializedName("strAwayGoalDetails")
        var awayGoalsDetails: String? = null,

        @SerializedName("intAwayShots")
        var awayShots: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var awayLineupGoalKeeper: String? = null,

        @SerializedName("strAwayLineupDefense")
        var awayLineupDefense: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var awayLineupMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        var awayLineupForward: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var awayLineupSubstitutes: String? = null,

        @SerializedName("strAwayRedCards")
        var awayRedCards: String? = null,

        @SerializedName("strAwayYellowCards")
        var awayYellowCards: String? = null,

        @SerializedName("strSport")
        var sport: String? = null
) : Parcelable