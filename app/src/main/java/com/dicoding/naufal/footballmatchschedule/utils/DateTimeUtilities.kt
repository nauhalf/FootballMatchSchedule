package com.dicoding.naufal.footballmatchschedule.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtilities {
    @SuppressLint("SimpleDateFormat")
    fun timeFormat(date: Date?) : String {
        val newFormat = SimpleDateFormat("HH:mm:ss")
        return newFormat.format(date)
    }


    @SuppressLint("SimpleDateFormat")
    fun dateFormat(date: Date?) : String {
        val newFormat = SimpleDateFormat("EEE, dd MMM yyyy")

        return newFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun dateParsing(d: String?, t: String?) : Date {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        format.timeZone = TimeZone.getTimeZone("UTC")

        val seconds = t?.split("+")?.get(0)
        val splitSeconds = seconds?.split(":")?.toMutableList()
        if(splitSeconds?.size!! < 3){
            splitSeconds.add("00")
        }
        val finalSeconds = splitSeconds.joinToString(":")

        return format.parse("$d $finalSeconds")
    }
}