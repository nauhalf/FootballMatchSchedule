package com.dicoding.naufal.footballmatchschedule.model.event

import com.google.gson.annotations.SerializedName

data class EventResponses(
        @SerializedName(value = "events", alternate = ["event"])
        val events: List<Event>? = null)