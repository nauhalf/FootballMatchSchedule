package com.dicoding.naufal.footballmatchschedule.model.favorite.favoritematch

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteMatch(

        val id: Long?,
        val idEvent: String?

) : Parcelable {

    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_MATCH"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
    }
}